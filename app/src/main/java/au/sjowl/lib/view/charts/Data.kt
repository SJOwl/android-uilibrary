package au.sjowl.lib.view.charts

import android.graphics.Color
import androidx.annotation.ColorInt

class ChartData {
    val title: String = "Followers"
    val columns: MutableMap<String, ChartColumn> = mutableMapOf()
    val x: ChartX = ChartX()
}

class ChartColumn(
    val id: String
) {
    var type: String = "line"
    var name: String = ""
    val values: ArrayList<Int> = arrayListOf()
    @ColorInt
    var color: Int = Color.RED
    var enabled: Boolean = true

    var min: Int = 0
    var max: Int = 0

    val height get() = max - min

    fun calculateBorders(start: Int = 0, end: Int = values.size) {
        min = Int.MAX_VALUE
        max = Int.MIN_VALUE

        for (i in start until end) {
            val v = values[i]
            if (v < min) min = v
            if (v > max) max = v
        }
    }
}

class ChartX {
    val values: ArrayList<Long> = arrayListOf()

    val min get() = values[0]
    val max get() = values[values.size - 1]
    val interval get() = max - min
}