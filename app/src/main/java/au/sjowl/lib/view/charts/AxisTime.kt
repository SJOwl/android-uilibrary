package au.sjowl.lib.view.charts

import android.graphics.Canvas
import android.graphics.Rect

class AxisTime(
    val layoutHelper: LayoutHelper,
    val chartRange: ChartRange
) {

    private val timeTitles = arrayListOf<String>()

    private val timeRectangles = arrayListOf<Rect>().apply {
        repeat(layoutHelper.xMarks + 1) { add(Rect()) }
    }

    private val dateFormatter = DateFormatter()

    fun draw(canvas: Canvas, top: Int) {
        val y = top * 1f + timeRectangles[0].height() + layoutHelper.paddingTextBottom

        val step = (layoutHelper.w - timeRectangles.sumBy { it.width() }) / (layoutHelper.xMarks)

        var x = 0f
        for (i in 0 until timeTitles.size) {
            val text = timeTitles[i]
            canvas.drawText(text, 0, text.length, x, y, layoutHelper.paintText)
            x += timeRectangles[i].width() + step
        }
    }

    fun onWindowChanged() {
        chartRange.timeInterval
        val start = chartRange.timeStart
        val step = chartRange.timeInterval / layoutHelper.xMarks
        timeTitles.clear()
        for (i in 0..layoutHelper.xMarks) {
            timeTitles.add(dateFormatter.format(start + step * i))
            layoutHelper.paintText.getTextBounds(timeTitles[i], 0, timeTitles[i].length, timeRectangles[i])
        }
    }
}