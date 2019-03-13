package au.sjowl.lib.view.charts

import android.graphics.Canvas
import android.graphics.Path

class Chart(
    val column: ChartColumn,
    val x: ChartX,
    val layoutHelper: LayoutHelper,
    val chartRange: ChartRange
) {
    private val path = Path()

    private val points = ArrayList<PointF>()

    fun draw(canvas: Canvas) {
        layoutHelper.paintChartLine.color = column.color
        with(path) {
            reset()
            if (points.size > 0) moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }
        canvas.drawPath(path, layoutHelper.paintChartLine)
    }

    fun drawPointer(canvas: Canvas) {
        layoutHelper.paintChartLine.color = column.color
        val i = chartRange.pointerTimeIndex
        canvas.drawCircle(points[i].x, points[i].y, layoutHelper.pointerCircleRadius, layoutHelper.paintBackground)
        canvas.drawCircle(points[i].x, points[i].y, layoutHelper.pointerCircleRadius, layoutHelper.paintChartLine)
    }

    fun getPointerX() = points[chartRange.pointerTimeIndex].x

    fun onWindowChanged(measuredWidth: Int, measuredHeight: Int) {
        val xmin = x.values[chartRange.timeIndexStart]
        val mh = measuredHeight * 1f - layoutHelper.paddingBottom
        val kX = measuredWidth * 1f / (x.values[chartRange.timeIndexEnd] - xmin)

        points.clear()

        val columnHeight = chartRange.interval

        val kY = 1f * (measuredHeight - layoutHelper.paddingBottom - layoutHelper.paddingTop) / columnHeight

        for (i in chartRange.timeIndexStart..chartRange.timeIndexEnd) {
            val x = kX * (x.values[i] - xmin)
            val y = mh - kY * (column.values[i] - chartRange.valueMin)
            points.add(PointF(x, y))
        }
    }
}