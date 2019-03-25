package au.sjowl.lib.ui.views.transitions

import android.view.View

class Range(
    var min: Int,
    var max: Int
) {

    var value: Int = min
        set(value) {
            field = when {
                value < min -> min
                value > max -> max
                else -> value
            }
        }

    var startValue: Int = value

    private val snapValue get() = if (max - value < value - min) max else min

    fun fixStartValue() {
        startValue = value
    }

    fun snap() {
        value = snapValue
    }

    fun correctMeasureSpec(measureSpec: Int): Int {
        value = View.MeasureSpec.getSize(measureSpec)
        return View.MeasureSpec.makeMeasureSpec(value, View.MeasureSpec.getMode(measureSpec))
    }
}