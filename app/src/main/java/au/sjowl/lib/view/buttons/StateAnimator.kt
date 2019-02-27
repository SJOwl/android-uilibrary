package au.sjowl.lib.view.buttons

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.util.ArrayMap
import android.view.View
import android.view.animation.DecelerateInterpolator
import au.sjowl.lib.view.utils.AnimProperty
import au.sjowl.lib.view.utils.AnimatedPropertyF
import au.sjowl.lib.view.utils.AnimatedPropertyInt

class StateAnimator {

    val colorBackground = AnimatedPropertyInt()

    val colorText = AnimatedPropertyInt() // 1

    private val animFloat = AnimatedPropertyF()

    private val animProperties = arrayListOf<AnimProperty>().apply {
        add(animFloat)
        add(colorBackground)
        add(colorText) //2
    }

    private val wrappers = arrayListOf<ValueAnimatorWrapper>()

    private var animatorSet: AnimatorSet? = AnimatorSet()

    private val animationDuration = 200L

    fun setAnimations(stateFrom: InteractionState, stateTo: InteractionState) {
        animFloat.from = 0f
        animFloat.to = 1f

        colorBackground.from = stateFrom.colorBackground
        colorBackground.to = stateTo.colorBackground // 3

        colorText.from = stateFrom.colorText
        colorText.to = stateTo.colorText

        wrappers.forEach { w ->
            //            w.from = stateFrom[w.key]
            //            w.to = stateTo[w.key]
        }

        if (animatorSet?.isRunning != true) {
            animProperties.forEach { it.setup() }
        }
    }

    fun animateSelectedState(view: View) {
        animatorSet?.cancel()
        animatorSet = AnimatorSet().apply {
            playTogether(
                valueAnim(animFloat, view),
                colorAnim(colorBackground, view),
                colorAnim(colorText, view) // 4
            )
            start()
        }
    }

    private fun valueAnim(animatedProperty: AnimatedPropertyF, view: View): ValueAnimator {
        return ValueAnimator.ofFloat(animatedProperty.from, animatedProperty.to).apply {
            duration = animationDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                animatedProperty.value = it.animatedValue as Float
                view.postInvalidate()
            }
        }
    }

    private fun colorAnim(animatedProperty: AnimatedPropertyInt, view: View) = ValueAnimator().apply {
        addUpdateListener {
            animatedProperty.value = it.animatedValue as Int
        }
        setIntValues(animatedProperty.from, animatedProperty.to)
        setEvaluator(ArgbEvaluator())
        duration = animationDuration
    }
}

/*
class InteractionState(
    val colorBackground: Int,
    val colorText: Int,
    val elevation: Float
)
 */

//val myState = ArrayMap<Int, Int>().apply {
//    put(COLOR_BACKGROUND, Color.WHITE)
//    put(COLOR_TEXT, Color.BLUE)
//}

class MyState {
    val COLOR_BACKGROUND = 1
    val COLOR_TEXT = 2
    val props = ArrayMap<Int, Int>().apply {
        put(COLOR_BACKGROUND, Color.WHITE)
        put(COLOR_TEXT, Color.BLUE)
    }
}

class ValueAnimatorWrapper(
    val key: Int = 0
) : ValueAnimator() {
    var from: Int = 0
    var to: Int = 0
}

