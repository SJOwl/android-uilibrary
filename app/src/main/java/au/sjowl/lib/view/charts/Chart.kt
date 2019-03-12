package au.sjowl.lib.view.charts

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path

class Chart(
    val column: ChartColumn,
    val x: ChartX,
    val layoutHelper: LayoutHelper,
    val chartRange: ChartRange
) {
    private val paintLine = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4f
        style = Paint.Style.STROKE
        color = column.color
    }

    private val path = Path()

    private val points = ArrayList<PointF>()

    fun draw(canvas: Canvas) {
        if (column.enabled) {
            with(path) {
                reset()
                if (points.size > 0) moveTo(points[0].x, points[0].y)
                for (i in 1 until points.size) {
                    lineTo(points[i].x, points[i].y)
                }
            }
            canvas.drawPath(path, paintLine)
        }
    }

    fun onWindowChanged(measuredWidth: Int, measuredHeight: Int) {
        val xmin = x.values[chartRange.timeIndexStart]
        val xInterval = x.values[chartRange.timeIndexEnd] - xmin
        val mh = measuredHeight * 1f - layoutHelper.paddingBottom
        val kX = measuredWidth * 1f / xInterval

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