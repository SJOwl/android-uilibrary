package au.sjowl.lib.view.animations

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator

open class AnimatedViewProperty<T>(
    val key: Int = 0,
    var from: T,
    var to: T,
    var animationDuration: Long = 180L
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

    open val floatInterpolator get() = DecelerateInterpolator()

    open fun animate(view: View): ValueAnimator {
        return when (from) {
            is Float -> fromFloat(view)
            is Int -> fromColor(view)
            else -> throw IllegalArgumentException("This type is not supported")
        }
    }

    private fun fromFloat(view: View) = ValueAnimator.ofFloat(from as Float, to as Float).apply {
        duration = animationDuration
        interpolator = floatInterpolator
        addUpdateListener {
            value = it.animatedValue as T
            invalidateView(view)
        }
    }

    private fun fromColor(view: View) = ValueAnimator().apply {
        duration = animationDuration
        setIntValues(from as Int, to as Int)
        setEvaluator(ArgbEvaluator())
        addUpdateListener {
            value = it.animatedValue as T
            invalidateView(view)
        }
    }

    private inline fun invalidateView(view: View) {
        if (view is ViewGroup) {
            view.requestLayout()
        } else {
            view.postInvalidate()
        }
    }
}