package au.sjowl.lib.view.charts

import android.graphics.Canvas
import android.graphics.Path

class Chart(
    val column: ChartColumn,
    val layoutHelper: LayoutHelper,
    val chartData: ChartData
) {

    protected val points = ArrayList<PointF>()

    protected val pointsFrom = ArrayList<PointF>()

    protected val path = Path()

    private var enabled = column.enabled

    private var alpha = 1f

    fun draw(canvas: Canvas) {
        if (column.enabled || enabled) {
            layoutHelper.paints.paintChartLine.color = column.color
            layoutHelper.paints.paintChartLine.alpha = (alpha * 255).toInt()
            canvas.drawPath(path, layoutHelper.paints.paintChartLine)
        }
    }

    fun drawPointer(canvas: Canvas) {
        layoutHelper.paints.paintChartLine.color = column.color
        val i = chartData.pointerTimeIndex
        canvas.drawCircle(points[i].x, points[i].y, layoutHelper.pointerCircleRadius, layoutHelper.paints.paintPointerCircle)
        canvas.drawCircle(points[i].x, points[i].y, layoutHelper.pointerCircleRadius, layoutHelper.paints.paintChartLine)
    }

    fun getPointerX() = points[chartData.pointerTimeIndex].x

    fun onWindowChanged() {
        update()
    }

    fun updateStartPoints() {
        pointsFrom.clear()
        points.mapTo(pointsFrom) { it }
        enabled = column.enabled
    }

    fun onAnimateValues(v: Float) {
        alpha = when {
            column.enabled && enabled -> 1f
            column.enabled && !enabled -> 1f - v
            !column.enabled && !enabled -> 0f
            else -> v
        }
        with(path) {
            reset()
            if (points.size > 0) moveTo(points[0].x, points[0].y + (pointsFrom[0].y - points[0].y) * v)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y + (pointsFrom[i].y - points[i].y) * v)
            }
        }
    }

    fun update() {
        val w = layoutHelper.w.toInt()
        val h = layoutHelper.h.toInt()
        val timeStart = chartData.timeIndexStart
        val timeEnd = chartData.timeIndexEnd

        val xmin = chartData.x.values[timeStart]
        val mh = h * 1f - layoutHelper.paddingBottom
        val kX = w * 1f / (chartData.x.values[timeEnd] - xmin)

        points.clear()

        val columnHeight = chartData.valueInterval

        val kY = 1f * (h - layoutHelper.paddingBottom - layoutHelper.paddingTop) / columnHeight

        for (i in timeStart..timeEnd) {
            val x = kX * (chartData.x.values[i] - xmin)
            val y = mh - kY * (column.values[i] - chartData.valueMin)
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

open class AbstractChart(
    val column: ChartColumn,
    val layoutHelper: LayoutHelper,
    val chartData: ChartData
) {
    protected val points = ArrayList<PointF>()
    protected val path = Path()

    fun update(w: Int, h: Int, timeStart: Int, timeEnd: Int) {
        val xmin = chartData.x.values[timeStart]
        val mh = h * 1f - layoutHelper.paddingBottom
        val kX = w * 1f / (chartData.x.values[timeEnd] - xmin)

        points.clear()

        val columnHeight = chartData.valueInterval

        val kY = 1f * (h - layoutHelper.paddingBottom - layoutHelper.paddingTop) / columnHeight

        for (i in timeStart..timeEnd) {
            val x = kX * (chartData.x.values[i] - xmin)
            val y = mh - kY * (column.values[i] - chartData.valueMin)
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