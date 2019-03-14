package au.sjowl.lib.view.charts

import android.graphics.Canvas
import android.graphics.Path

class Chart(
    column: ChartColumn,
    x: ChartX,
    layoutHelper: LayoutHelper,
    chartRange: ChartRange
) : AbstractChart(column, x, layoutHelper, chartRange) {

    fun draw(canvas: Canvas) {
        layoutHelper.paintChartLine.color = column.color
        canvas.drawPath(path, layoutHelper.paintChartLine)
    }

    fun drawPointer(canvas: Canvas) {
        layoutHelper.paintChartLine.color = column.color
        val i = chartRange.pointerTimeIndex
        canvas.drawCircle(points[i].x, points[i].y, layoutHelper.pointerCircleRadius, layoutHelper.paintPointerCircle)
        canvas.drawCircle(points[i].x, points[i].y, layoutHelper.pointerCircleRadius, layoutHelper.paintChartLine)
    }

    fun getPointerX() = points[chartRange.pointerTimeIndex].x

    fun onWindowChanged() {
        update(layoutHelper.w.toInt(), layoutHelper.h.toInt(), chartRange.timeIndexStart, chartRange.timeIndexEnd)
    }
}

open class AbstractChart(
    val column: ChartColumn,
    val x: ChartX,
    val layoutHelper: LayoutHelper,
    val chartRange: ChartRange
) {
    protected val points = ArrayList<PointF>()
    protected val path = Path()

    fun update(w: Int, h: Int, timeStart: Int, timeEnd: Int) {
        val xmin = x.values[timeStart]
        val mh = h * 1f - layoutHelper.paddingBottom
        val kX = w * 1f / (x.values[timeEnd] - xmin)

        points.clear()

        val columnHeight = chartRange.valueInterval

        val kY = 1f * (h - layoutHelper.paddingBottom - layoutHelper.paddingTop) / columnHeight

        for (i in timeStart..timeEnd) {
            val x = kX * (x.values[i] - xmin)
            val y = mh - kY * (column.values[i] - chartRange.valueMin)
            points.add(PointF(x, y))
        }

        updatePath()
    }

    private fun updatePath() {
        with(path) {
            reset()
            if (points.size > 0) moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }
    }
}