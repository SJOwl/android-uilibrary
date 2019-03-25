package au.sjowl.lib.ui.views.animations

import android.animation.AnimatorSet
import android.view.View
import au.sjowl.lib.ui.views.animations.interpolators.providers.DefaultInterpolatorProvider
import au.sjowl.lib.ui.views.animations.interpolators.providers.InterpolatorProvider

open class ViewStateAnimator(
    var animationDuration: Long = 200L,
    val interpolatorProvider: InterpolatorProvider = DefaultInterpolatorProvider()
) {

    protected open val properties = mutableMapOf<Int, AnimatedViewProperty<Any>>()

    protected var animatorSet: AnimatorSet? = AnimatorSet()

    fun getColor(key: Int): Int = properties.getValue(key).value as Int

    fun getFloat(key: Int): Float = properties.getValue(key).value as Float

    // todo create base State(){ val properties:Map<Int, Any>}
    fun setStates(stateFrom: Map<Int, Any>, stateTo: Map<Int, Any>) {

        properties.clear()
        stateFrom.keys.forEach { key ->
            properties[key] = AnimatedViewProperty(
                key = key,
                from = stateFrom.getValue(key),
                to = stateTo.getValue(key),
                animationDuration = animationDuration,
                interpolatorProvider = interpolatorProvider
            )
        }

        properties.values.forEach { w ->
            when (w.from) {
                is Int -> {
                    w.from = stateFrom[w.key] as Int
                    w.to = stateTo[w.key] as Int
                }
                is Float -> {
                    w.from = stateFrom[w.key] as Float
                    w.to = stateTo[w.key] as Float
                }
            }
        }

        if (animatorSet?.isRunning != true) {
            properties.values.forEach { it.setup() }
        }
    }

    open fun animate(view: View) {
        animatorSet?.cancel()
        animatorSet = AnimatorSet().apply {
            playTogether(properties.values.map { it.animate(view) })
            start()
        }
    }
}