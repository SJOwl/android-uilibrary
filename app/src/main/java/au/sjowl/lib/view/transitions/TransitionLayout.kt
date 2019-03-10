package au.sjowl.lib.view.transitions

import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import au.sjowl.lib.view.app.test.touch.eventToAction
import au.sjowl.lib.view.utils.constrain
import kotlinx.android.synthetic.main.fr_transition_top_menu.view.*

class TransitionLayout : ConstraintLayout {

    private val transition = ChangeBounds().apply {
        duration = 180L
        interpolator = OvershootInterpolator()
    }

    private val constraintSet = ConstraintSet()

    private var downY = 0f

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        constrain(constraintSet) { cs ->
            cs.clear(content.id, ConstraintSet.BOTTOM)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        content.layoutParams.height = measuredHeight - menu.heightMin
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        println("action dispatchTouchEvent ${eventToAction(event)}")

        when (event.action) {
            MotionEvent.ACTION_UP -> {
                if (Math.abs(event.y - downY) > 10f) {
                    menu.onSetStateToEdgeValues()
                    requestLayoutWithTransition()
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        println("action ${eventToAction(event)}")

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dir = event.y - downY
                if (!content.canScrollVertically(-dir.toInt())) {
                    menu.increaseSize(0f, event.y - downY)
                    requestLayout()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (Math.abs(event.y - downY) > 10f) {
                    menu.onSetStateToEdgeValues()
                    requestLayoutWithTransition()
                }
            }
        }
        return false
    }

    fun requestLayoutWithTransition() {
        TransitionManager.beginDelayedTransition(this, transition)
        requestLayout()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}