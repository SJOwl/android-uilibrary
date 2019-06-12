package au.sjowl.lib.ui.views.utils

import android.content.Context
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import kotlin.system.measureNanoTime

inline fun absCos(value: Float) = Math.abs(Math.cos(value * 1.0)).toFloat()
inline fun absCos(value: Double) = Math.abs(Math.cos(value)).toFloat()

inline fun absSin(value: Float) = Math.abs(Math.sin(value * 1.0)).toFloat()
inline fun absSin(value: Double) = Math.abs(Math.sin(value)).toFloat()

inline fun View.contains(px: Int, py: Int): Boolean {
    return px > x && px < x + measuredWidth && py > y && py < y + measuredHeight
}

inline fun View.contains(px: Float, py: Float): Boolean {
    return px > x && px < x + width && py > y && py < y + height
}

inline fun View.contains(event: MotionEvent): Boolean {
    return contains(event.x + x, event.y + y)
}

interface AnimProperty {
    fun setup()
    fun reverse()
}

class AnimatedPropertyF(
    var value: Float = 0f,
    var from: Float = 0f,
    var to: Float = 0f
) : AnimProperty {
    override fun setup() {
        value = from
    }

    override fun reverse() {
        val t = from
        from = to
        to = t
    }
}

class AnimatedPropertyInt(
    var value: Int = 0,
    var from: Int = 0,
    var to: Int = 0
) : AnimProperty {
    override fun setup() {
        value = from
    }

    override fun reverse() {
        val t = from
        from = to
        to = t
    }

    fun set(from: Int, to: Int, value: Int) {
        this.from = from
        this.to = to
        this.value = value
    }
}

fun Context.colorCompat(id: Int) = ContextCompat.getColor(this, id)

fun measureDrawingMs(msg: String, block: (() -> Unit)) {
    val t = measureNanoTime {
        block.invoke()
    }
    println("$msg draw %.3f".format(t / 1000000f))
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.scale(scale: Float) {
    scaleX = scale
    scaleY = scale
}

fun ConstraintLayout.constrain(cs: ConstraintSet, transition: Transition, block: ((cs: ConstraintSet) -> Unit)) {
    cs.clone(this)
    if (android.os.Build.VERSION.SDK_INT >= 19) {
        TransitionManager.beginDelayedTransition(this, transition)
    }
    block.invoke(cs)
    cs.applyTo(this)
}

fun ConstraintLayout.constrain(cs: ConstraintSet, block: ((cs: ConstraintSet) -> Unit)) {
    cs.clone(this)
    if (android.os.Build.VERSION.SDK_INT >= 19) {
        val transition = AutoTransition().apply {
            duration = 500L
        }
        TransitionManager.beginDelayedTransition(this, transition)
    }
    block.invoke(cs)
    cs.applyTo(this)
}