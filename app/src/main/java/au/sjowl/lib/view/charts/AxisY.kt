package au.sjowl.lib.view.charts

import android.graphics.Canvas

class AxisY(
    private val layoutHelper: LayoutHelper,
    var chartData: ChartData
) {

    private val valueFormatter = ValueFormatter()

    private var pointsFrom = arrayListOf<CanvasPoint>()

    private var pointsTo = arrayListOf<CanvasPoint>()

    private val historyRange = HistoryRange()

    private var animFloat = 0f

    fun draw(canvas: Canvas) {
        layoutHelper.paints.paintGrid.alpha = ((1f - animFloat) * 255).toInt()
        layoutHelper.paints.paintText.alpha = ((1f - animFloat) * 255).toInt()
        for (i in 0 until pointsTo.size) {
            canvas.drawLine(0f, pointsTo[i].canvasValue, layoutHelper.w, pointsTo[i].canvasValue, layoutHelper.paints.paintGrid)
            canvas.drawText(valueFormatter.format(pointsTo[i].value), 0f, pointsTo[i].canvasValue - layoutHelper.paddingTextBottom, layoutHelper.paints.paintText)
        }

        layoutHelper.paints.paintGrid.alpha = (animFloat * 255).toInt()
        layoutHelper.paints.paintText.alpha = (animFloat * 255).toInt()
        for (i in 0 until pointsFrom.size) {
            canvas.drawLine(0f, pointsFrom[i].canvasValue, layoutHelper.w, pointsFrom[i].canvasValue, layoutHelper.paints.paintGrid)
            canvas.drawText(valueFormatter.format(pointsFrom[i].value), 0f, pointsFrom[i].canvasValue - layoutHelper.paddingTextBottom, layoutHelper.paints.paintText)
        }
    }

    fun updateStartPoints() {
        // save all old points
        pointsFrom.clear()
        pointsTo.mapTo(pointsFrom) { it }

        historyRange.minStart = chartData.valueMin
        historyRange.maxStart = chartData.valueMax
    }

    fun adjustValuesRange(min: Int, max: Int) {
        pointsTo.clear()
        // get new points
        valueFormatter
            .marksFromRange(min, max, layoutHelper.yMarks)
            .mapTo(pointsTo) { CanvasPoint(it) }

        chartData.valueMin = pointsTo[0].value
        chartData.valueMax = pointsTo.last().value

        historyRange.minEnd = chartData.valueMin
        historyRange.maxEnd = chartData.valueMax
    }

    fun onAnimateValues(v: Float) { // v: 1 -> 0
        animFloat = v
        val kY = 1f * (layoutHelper.h - layoutHelper.paddingBottom - layoutHelper.paddingTop) / (historyRange.endInterval - historyRange.deltaInterval * v)
        val mh = layoutHelper.h * 1f - layoutHelper.paddingBottom
        var min = historyRange.minEnd + v * historyRange.deltaMin
        // scale new points
        pointsTo.forEach { point -> point.canvasValue = mh - kY * (point.value - min) }
        // rescale old points
        min = historyRange.minEnd + (1f - v) * historyRange.deltaMin
        pointsFrom.forEach { point -> point.canvasValue = mh - kY * (point.value - min) }
    }
}

class HistoryRange {
    var minStart = 0
    var minEnd = 0
    var maxStart = 0
    var maxEnd = 0
    val startInterval get() = maxStart - minStart
    val endInterval get() = maxEnd - minEnd
    val deltaInterval get() = endInterval - startInterval
    val deltaMin get() = minEnd - minStart
}

class CanvasPoint(
    var value: Int = 0,
    var canvasValue: Float = 0f
)

class ValueFormatter {
    fun stepFromRange(min: Int, max: Int, marksSize: Int): Int {
        val interval = max - min

        var i = 0
        var stop = false
        var s = 0

        while (!stop) {
            s = stepFromIndex(i)
            val t = interval / s

            for (j in 1..marksSize * 2) {
                if (t in 0..marksSize * j) {
                    s *= j
                    stop = true
                    break
                }
            }
            i++
        }
        return s
    }

    fun marksFromRange(min: Int, max: Int, marksSize: Int): ArrayList<Int> {
        val step = stepFromRange(min, max, marksSize)
        val minAdjusted = min - min % step
        val maxAdjusted = if (max % step == 0) max else max - (max + step) % step + step
        val stepAdjusted = stepFromRange(minAdjusted, maxAdjusted, marksSize)

        val list = arrayListOf<Int>()
        for (i in 0..5) list.add(stepAdjusted * i + minAdjusted)

        return list
    }

    fun format(value: Int): String {
        return when (value) {
            0 -> ""
            in 1..999 -> value.toString()
            in 1000..999_999 -> "${removeTrailingZeroes("%.1f".format(value / 1000f).toFloat())}k"
            in 1_000_000..999_999_999 -> "${removeTrailingZeroes("%.1f".format(value / 1_000_000f).toFloat())}M"
            else -> value.toString()
        }
    }

    private fun removeTrailingZeroes(v: Float): String =
        if (v == v.toLong().toFloat()) String.format("%d", v.toLong())
        else String.format("%s", v)

    private fun stepFromIndex(index: Int): Int {
        return if (index == 0) {
            1
        } else {
            val i = index - 1
            (i + 1) % 2 * 5 * Math.pow(10.0, (i / 2).toDouble()).toInt() + (i) % 2 * 10 * Math.pow(10.0, (i / 2).toDouble()).toInt()
        }
    }
}