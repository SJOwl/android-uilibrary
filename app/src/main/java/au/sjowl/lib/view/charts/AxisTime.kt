package au.sjowl.lib.view.charts

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Locale

class AxisTime(
    val layoutHelper: LayoutHelper,
    val chartRange: ChartRange
) {

    private val timeTitles = arrayListOf<String>()
    private val timeRectangles = arrayListOf<Rect>().apply {
        repeat(layoutHelper.xMarks + 1) { add(Rect()) }
    }

    private val calendar = GregorianCalendar()

    private val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())

    private val paintText = Paint().apply {
        isAntiAlias = true
        color = layoutHelper.colorText
        textSize = layoutHelper.textSize.toFloat()
    }

    fun draw(canvas: Canvas, measuredWidth: Int, measuredHeight: Int) {
        val y = (measuredHeight - timeRectangles[0].height()) * 1f

        val step = (measuredWidth - timeRectangles.sumBy { it.width() }) / (layoutHelper.xMarks)

        var x = 0f
        for (i in 0 until timeTitles.size) {
            val text = timeTitles[i]
            canvas.drawText(text, 0, text.length, x, y, paintText)
            x += timeRectangles[i].width() + step
        }
    }

    fun onWindowChanged() {
        chartRange.timeInterval
        val start = chartRange.timeStart
        val step = chartRange.timeInterval / layoutHelper.xMarks
        timeTitles.clear()
        for (i in 0..layoutHelper.xMarks) {
            timeTitles.add(formatDate(start + step * i))
            paintText.getTextBounds(timeTitles[i], 0, timeTitles[i].length, timeRectangles[i])
        }
    }

    fun formatDate(timeInMillisec: Long): String {
        calendar.timeInMillis = timeInMillisec
        return dateFormat.format(calendar.time)
    }
}