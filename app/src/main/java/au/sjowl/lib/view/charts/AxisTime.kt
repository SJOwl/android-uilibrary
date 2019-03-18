package au.sjowl.lib.view.charts

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class AxisTime(
    private val layoutHelper: LayoutHelper,
    private val chartRange: ChartRange
) {

    private val rectText = Rect()

    private var y = 0f

    private val scalablePoints = arrayListOf<ScalablePoint>()

    private val paintText = Paint(layoutHelper.paintText)

    private var defaultDistance = 1f

    fun draw(canvas: Canvas) {
        for (i in 0 until scalablePoints.size) {
            val t = scalablePoints[i]
            paintText.alpha = (t.alpha * 255).toInt()
            canvas.drawText(t.title, 0, t.title.length, t.x * layoutHelper.w - rectText.width() / 2, y, paintText)
        }
    }

    fun onWindowChanged() {
        layoutHelper.paintText.getTextBounds("Mar 222", 0, 6, rectText)
        if (chartRange.scaleInProgress) {
            println("before ${scalablePoints.map { it.x }}")
            onScale()
            println("after  ${scalablePoints.map { it.x }}")
        } else if (!chartRange.scaleInProgress) {
            onScaleEnd()
        }
    }

    private fun onScale() {
        if (scalablePoints.size < 3) throw IllegalStateException("scalablePoints is empty: ${scalablePoints.map { it.x }}")

        val w = (if (layoutHelper.w > 0) layoutHelper.w else 100f).toLong()
        val halfText = rectText.width() / 2
        val t0 = w / halfText

        val start: Long = chartRange.timeInterval / t0 + chartRange.timeStart
        val timeInterval = chartRange.timeInterval - 2 * (start - chartRange.timeStart)

        scalablePoints.forEach {
            it.x = it.xStart + 1f * (it.t - start) / timeInterval - 1f * (it.t - it.start) / it.interval
        }

        addBorderPoints()

        val distanceScale = Math.abs(scalablePoints[1].x - scalablePoints[0].x) / defaultDistance
        println("dist = $distanceScale")
        when {
            distanceScale in 0f..0.5f -> {
                scalablePoints.removeAll(scalablePoints.filter { it.toFade })
                if (scalablePoints.size < 3) throw IllegalStateException("scalablePoints is empty: ${scalablePoints.map { it.x }}")
                for (i in 0 until scalablePoints.size) {
                    scalablePoints[i].toFade = i % 2 == 0
                }
                val newDistanceScale = (scalablePoints[1].x - scalablePoints[0].x) / defaultDistance
                scalablePoints.forEach { p -> if (p.toFade) p.alpha = newDistanceScale }
            }
            distanceScale in 0.5f..1f -> {
                val a = 4f * distanceScale - 3f
                scalablePoints.forEach { p -> if (p.toFade) p.alpha = a }
            }
            distanceScale > 1.5f -> {
                scalablePoints.forEach { it.toFade = false }
                for (i in scalablePoints.size - 2 downTo 0) {
                    addPointBetween(i)
                }
            }
        }
        val h = 2 / t0
        val filtered = scalablePoints.filter { !(it.x > -h && it.x < 1 + h) }
        if (scalablePoints.size - filtered.size < 3) throw IllegalStateException("scalablePoints is empty: ${scalablePoints.map { it.x }}")
        scalablePoints.removeAll(filtered)
        if (scalablePoints.size < 3) throw IllegalStateException("scalablePoints is empty: ${scalablePoints.map { it.x }}")

//        if (scalablePoints.size > layoutHelper.xMarks * 2) println("too many points: ${scalablePoints.size}")
//        if (scalablePoints.size < layoutHelper.xMarks) println("too few points: ${scalablePoints.size}")
    }

    private fun onScaleEnd() {
        val w = (if (layoutHelper.w > 0) layoutHelper.w else 100f).toLong()
        val halfText = rectText.width() / 2
        val t0 = w / halfText
        val start: Long = chartRange.timeInterval / t0 + chartRange.timeStart
        val timeInterval = chartRange.timeInterval - 2 * (start - chartRange.timeStart)

        val step = timeInterval / layoutHelper.xMarks
        scalablePoints.clear()
        for (i in 0..layoutHelper.xMarks) {

            val t = step * i + start
            scalablePoints.add(ScalablePoint(
                t = t,
                xStart = 1f * (t - chartRange.timeStart) / chartRange.timeInterval,
                interval = timeInterval,
                start = start
            ))
        }

        y = layoutHelper.h - layoutHelper.paddingBottom + rectText.height() + layoutHelper.paddingTextBottom
        defaultDistance = scalablePoints[1].x - scalablePoints[0].x
        for (i in 1 until scalablePoints.size step 2) {
            scalablePoints[i].toFade = true
        }

        // todo calculate new scalablePoints, animate changes
    }

    private fun addPointBetween(i: Int) {
        val f = scalablePoints[i]
        val dt = scalablePoints[i + 1].t - scalablePoints[i].t
        val dx = scalablePoints[i + 1].x - scalablePoints[i].x
        val dxStart = scalablePoints[i + 1].xStart - scalablePoints[i].xStart

        scalablePoints.add(i + 1, ScalablePoint(
            t = f.t + dt / 2,
            xStart = f.xStart + dxStart / 2,
            x = f.x + dx / 2,
            interval = f.interval,
            start = f.start,
            toFade = true,
            alpha = 0.01f
        ))
    }

    private fun addBorderPoints() {
        while (scalablePoints[0].x > -0.5) {
            val f = scalablePoints[0]
            scalablePoints.add(0, ScalablePoint(
                t = f.t - (scalablePoints[1].t - scalablePoints[0].t),
                xStart = f.xStart - (scalablePoints[1].xStart - scalablePoints[0].xStart),
                x = f.x - (scalablePoints[1].x - scalablePoints[0].x),
                interval = f.interval,
                start = f.start,
                toFade = !f.toFade
            ))
        }

        while (scalablePoints.last().x < 1.5) {
            val l = scalablePoints.last()
            val i = scalablePoints.lastIndex
            scalablePoints.add(ScalablePoint(
                t = l.t + (scalablePoints[i].t - scalablePoints[i - 1].t),
                xStart = l.xStart + (scalablePoints[i].xStart - scalablePoints[i - 1].xStart),
                x = l.x + (scalablePoints[i].x - scalablePoints[i - 1].x),
                interval = l.interval,
                start = l.start,
                toFade = !l.toFade
            ))
        }
    }
}

class ScalablePoint(
    var t: Long = 0L,
    var xStart: Float = 0f,
    var x: Float = xStart, // [0;1]
    var alpha: Float = 1f,
    var interval: Long = 0L,
    var start: Long = 0L,
    var toFade: Boolean = false
) {
    var title: String = DateFormatter.format(t)
}