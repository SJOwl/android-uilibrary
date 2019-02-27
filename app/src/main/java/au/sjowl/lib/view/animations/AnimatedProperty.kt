package au.sjowl.lib.view.animations

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator

class AnimatedProperty<T>(
    val key: Int = 0,
    var from: T,
    var to: T,
    val animationDuration: Long = 180L
) {
    var value = from

    fun setup() {
        value = from
    }

    fun reverse() {
        val temp = from
        from = to
        to = temp
    }

    fun animate(view: View): ValueAnimator {
        return when (from) {
            is Float -> fromFloat(view)
            is Int -> fromColor(view)
            else -> throw IllegalArgumentException("This type is not supported")
        }
    }

    private fun fromFloat(view: View) = ValueAnimator.ofFloat(from as Float, to as Float).apply {
        duration = animationDuration
        interpolator = DecelerateInterpolator()
        addUpdateListener {
            value = it.animatedValue as T
            view.postInvalidate()
        }
    }

    private fun fromColor(view: View) = ValueAnimator().apply {
        duration = animationDuration
        setIntValues(from as Int, to as Int)
        setEvaluator(ArgbEvaluator())
        addUpdateListener {
            value = it.animatedValue as T
            view.postInvalidate()
        }
    }
}