package au.sjowl.lib.view.charts

import org.junit.Test
import java.util.GregorianCalendar
import kotlin.test.assertEquals

class AxisTimeTest {
    val axisTime = AxisTime(LayoutHelper(), ChartRange())

    @Test
    fun formatDateTest() {
        assertEquals(axisTime.formatDate(GregorianCalendar(2019, 2, 12).timeInMillis), "Mar 12")
    }
}