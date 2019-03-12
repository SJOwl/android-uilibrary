package au.sjowl.lib.view.charts

import org.junit.Test
import kotlin.test.assertEquals

class AxisYTest {
    val axisY = AxisY(LayoutHelper())

    @Test
    fun marksFromRangeTest() {
        assertEquals(axisY.marksFromRange(1, 250), arrayListOf(0, 50, 100, 150, 200, 250))
        assertEquals(axisY.marksFromRange(1, 252), arrayListOf(0, 60, 120, 180, 240, 300))
        assertEquals(axisY.marksFromRange(1, 50), arrayListOf(0, 10, 20, 30, 40, 50))
        assertEquals(axisY.marksFromRange(1, 100), arrayListOf(0, 20, 40, 60, 80, 100))
        assertEquals(axisY.marksFromRange(1, 105), arrayListOf(0, 25, 50, 75, 100, 125))
        assertEquals(axisY.marksFromRange(1, 102), arrayListOf(0, 25, 50, 75, 100, 125))
        assertEquals(axisY.marksFromRange(1, 110), arrayListOf(0, 25, 50, 75, 100, 125))
        assertEquals(axisY.marksFromRange(5, 99), arrayListOf(0, 20, 40, 60, 80, 100))
        assertEquals(axisY.marksFromRange(26, 278), arrayListOf(0, 60, 120, 180, 240, 300))
    }

//    @Test
//    fun stepFromRangeTest() {
//        axisY.stepFromRange(1, 250) shouldBe 50
//        axisY.stepFromRange(10, 250) shouldBe 50
//        axisY.stepFromRange(10, 230) shouldBe 50
//        axisY.stepFromRange(5, 99) shouldBe 20
//        axisY.stepFromRange(5, 10) shouldBe 1
//        axisY.stepFromRange(5, 200) shouldBe 40
//    }
}