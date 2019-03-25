package au.sjowl.lib.ui.views.transitions

import android.content.Context
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.fr_transition_top_menu.view.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sp

class MenuViewGroup : ConstraintLayout, StateView {

    override val heightMax = context.dip(200)

    override val heightMin = context.dip(56)

    override val heightRange = Range(heightMin, heightMax)

    private val transition = ChangeBounds().apply {
        duration = 180L
        interpolator = OvershootInterpolator()
    }

    private val menuItems = listOf(
        MenuData(0, "Dashboard"),
        MenuData(1, "History"),
        MenuData(2, "Statistics")
    )

    private val menuViews = menuItems.map {
        MenuItemTextView(context).apply {
            text = it.title
            menuItemId = it.commandId
        }
    }

    private val iconWidth = context.dip(40) * 1f

    private var baseTitleHeight = context.sp(12) * 1f

    private var onMenuItemClickListener: ((index: Int) -> Unit)? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val m = menuItemsContainer
        menuViews.forEach { m.addView(it) }
        menuViews.forEach {
            with(it) {
                onClick { onMenuItemClick(menuItemId) }
                setPadding((iconWidth / 2).toInt(), paddingTop, paddingRight, paddingBottom)
                invalidate()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightRange.correctMeasureSpec(heightMeasureSpec))
        val x = 1f * (measuredHeight - heightMin) / (heightMax - heightMin)
        val mult = x * x
        val paddingText = Math.cbrt(x * 1.0)
        menuViews.forEach {
            with(it) {
                textSize = baseTitleHeight * mult
                setPadding((paddingText * iconWidth * 1.5f).toInt(), paddingTop, paddingRight, paddingBottom)
                alpha = mult * 1f
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return super.onInterceptTouchEvent(ev)
        // todo click through rv
    }

    override fun increaseSize(deltaX: Float, deltaY: Float) {
        heightRange.value = (heightRange.startValue + deltaY).toInt()
        layoutParams.height = heightRange.value
    }

    override fun onSetStateToEdgeValues() {
        heightRange.value = measuredHeight
        heightRange.snap()
        heightRange.fixStartValue()
        layoutParams.height = heightRange.value
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        menuItemsContainer.removeAllViews()
    }

    fun onMenuItemClick(listener: ((index: Int) -> Unit)?) {
        onMenuItemClickListener = listener
    }

    private fun onMenuItemClick(id: Int) {
        layoutParams.height = heightMin
        TransitionManager.beginDelayedTransition(parent as ConstraintLayout, transition)
        parent.requestLayout()

        onMenuItemClickListener?.invoke(id)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}