package au.sjowl.lib.view.charts

import android.graphics.Canvas
import android.graphics.Paint

class AxisY(
    private val layoutHelper: LayoutHelper,
    private val chartRange: ChartRange
) {
    private var marks = arrayListOf<Float>()

    private var points = arrayListOf<Int>()

    private val paintLines = Paint().apply {
        isAntiAlias = true
        color = layoutHelper.colorLines
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    private val paintText = Paint().apply {
        isAntiAlias = true
        color = layoutHelper.colorText
        textSize = layoutHelper.textSize.toFloat()
    }

    fun draw(canvas: Canvas, measuredWidth: Int) {
        for (i in 0 until marks.size) {
            canvas.drawLine(0f, marks[i], measuredWidth.toFloat(), marks[i], paintLines)
            canvas.drawText(points[i].toString(), 0f, marks[i] - layoutHelper.paddingTextBottom, paintText)
        }
    }

    fun onWindowChanged(min: Int, max: Int, measuredHeight: Int) {
        points = marksFromRange(min, max)
        chartRange.valueMin = points[0]
        chartRange.valueMax = points.last()
        val kY = 1f * (measuredHeight - layoutHelper.paddingBottom - layoutHelper.paddingTop) / (chartRange.interval)
        val mh = measuredHeight * 1f - layoutHelper.paddingBottom
        marks.clear()
        points.mapTo(marks) { mh - kY * (it - chartRange.valueMin) }
    }

    fun marksFromRange(min: Int, max: Int): ArrayList<Int> {
        val step = stepFromRange(min, max)
        val minAdjusted = min - min % step
        val maxAdjusted = if (max % step == 0) max else max - (max + step) % step + step
        val stepAdjusted = stepFromRange(minAdjusted, maxAdjusted)

        val list = arrayListOf<Int>()
        for (i in 0..5) list.add(stepAdjusted * i + minAdjusted)

        println("[$min;$max] -> [$minAdjusted;$maxAdjusted], $step->$stepAdjusted, $list")
        return list
    }

    private fun stepFromRange(min: Int, max: Int): Int {
        val interval = max - min

        var i = 0
        var stop = false
        var s = 0

        while (!stop) {
            s = stepFromIndex(i)
            val t = interval / s

            for (j in 1..STEPS * 2) {
                if (t in 0..STEPS * j) {
                    s *= j
                    stop = true
                    break
                }
            }
            i++
        }
        return s
    }

    private fun stepFromIndex(index: Int): Int {
        return if (index == 0) {
            1
        } else {
            val i = index - 1
            (i + 1) % 2 * 5 * Math.pow(10.0, (i / 2).toDouble()).toInt() + (i) % 2 * 10 * Math.pow(10.0, (i / 2).toDouble()).toInt()
        }
    }

    companion object {
        const val STEPS = 5
    }
}