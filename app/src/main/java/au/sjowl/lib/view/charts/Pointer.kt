package au.sjowl.lib.view.charts

import android.graphics.Canvas

class Pointer(
    val layoutHelper: LayoutHelper,
    val chartData: ChartData
) {
    fun draw(canvas: Canvas) {
        // line
        canvas.drawLine(chartData.pointerTimeX, layoutHelper.h, chartData.pointerTimeX, 0f, layoutHelper.paints.paintGrid)

        // circles for each chart

        // popup
    }
}