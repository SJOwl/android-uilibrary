package au.sjowl.lib.ui.views.utils

import java.util.LinkedList
import kotlin.math.roundToLong

class CycleArray(val size: Int = 10) {
    private val array = LinkedList<Long>()

    fun add(value: Long) {
        if (array.size >= size) {
            array.removeAt(0)
        }
        array.add(value)
    }

    fun average(): Long {
        var sum: Double = 0.0
        var count: Int = 0
        for (element in array) {
            sum += element
            ++count
        }
        return if (count == 0) -1 else (sum / count).roundToLong()
    }
}