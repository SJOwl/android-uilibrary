package au.sjowl.lib.view.charts

import java.text.SimpleDateFormat
import java.util.GregorianCalendar
import java.util.Locale

class DateFormatter {
    private val calendar = GregorianCalendar()
    private val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())

    fun format(timeInMillisec: Long): String {
        calendar.timeInMillis = timeInMillisec
        return dateFormat.format(calendar.time)
    }
}