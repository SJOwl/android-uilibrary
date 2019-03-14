package au.sjowl.lib.view.charts

import android.graphics.Canvas

class Pointer(
    val layoutHelper: LayoutHelper,
    val chartRange: ChartRange
) {
    fun draw(canvas: Canvas) {
        // line
        canvas.drawLine(chartRange.pointerTimeX, layoutHelper.h, chartRange.pointerTimeX, 0f, layoutHelper.paintGrid)

        // circles for each chart

        // popup
    }
}