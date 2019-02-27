package au.sjowl.lib.view.animations

import android.animation.AnimatorSet
import android.view.View

class StateAnimator(
    var animationDuration: Long = 200L
) {

    val properties = mutableMapOf<Int, AnimatedProperty<Any>>()

    private var animatorSet: AnimatorSet? = AnimatorSet()

    inline fun getInt(key: Int): Int = properties.getValue(key).value as Int

    inline fun getFloat(key: Int): Float = properties.getValue(key).value as Float

    fun setStates(stateFrom: Map<Int, Any>, stateTo: Map<Int, Any>) {

        properties.clear()
        stateFrom.keys.forEach { key ->
            properties.put(key, AnimatedProperty(
                key = key,
                from = stateFrom.getValue(key),
                to = stateTo.getValue(key),
                animationDuration = animationDuration
            ))
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

    fun animate(view: View) {
        animatorSet?.cancel()
        animatorSet = AnimatorSet().apply {
            playTogether(properties.values.map { it.animate(view) })
            start()
        }
    }
}