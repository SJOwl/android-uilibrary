package au.sjowl.lib.view.buttons.fab.vertical

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import au.sjowl.lib.view.animations.ViewStateAnimator
import au.sjowl.lib.view.utils.gone
import au.sjowl.lib.view.utils.show
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick

class VerticalMenuFab : ViewGroup {

    val items = arrayListOf<FabMenuView>()

    var fabIconId = 0
        set(value) {
            field = value
            fab.icon = ContextCompat.getDrawable(context, value) as Drawable
        }

    val animator = ViewStateAnimator(1800L)

    private val fab = FabView(context)

    private val baseWidth = context.dip(56)

    private val marginItems = context.dip(16)

    private val multiplier get() = animator.getFloat(FmState.MULT) // [0f;1f]

    private val stateCollapsed = FmStateCollapsed()

    private val stateExpanded = FmStateExpanded()

    private var stateCurrent: FmState = stateCollapsed

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var l = right - baseWidth - paddingRight
        var t = bottom - baseWidth - paddingBottom
        val r = l + baseWidth
        fab.layout(l, t, r, t + baseWidth)
        fab.postInvalidate()

        for ((index, it) in items.withIndex()) {
            if (multiplier > 0f) {
                it.show()
                val cx = right - baseWidth / 2 - paddingRight
                val cy = bottom - baseWidth / 2 - paddingBottom
                l = right - baseWidth - paddingRight
                t = (bottom - (paddingBottom + (baseWidth * (index + 2) + marginItems * (index + 1)) * multiplier)).toInt()
                it.layout(l, t, r, t + baseWidth)
                it.rotation = (1f - multiplier) * 180
                it.scaleX = (multiplier + 1f) / 2
                it.scaleY = it.scaleX
            } else {
                it.gone()
            }
        }
    }

    fun setMenuItems(items: Collection<FabMenuItem>) {
        this.items.clear()
        removeAllViews()

        items.forEach {
            val menuItem = FabMenuView(context).apply {
                data = it
                animator = this@VerticalMenuFab.animator
            }
            this.items.add(menuItem)
            addView(menuItem)
        }
        addView(fab)
        invalidate()
    }

    fun onItemSelected(function: (Int) -> Unit) {
    }

    private fun setState(state: FmState) {
        if (state == stateCurrent) return
        animator.setStates(stateCurrent.properties, state.properties)
        animator.animate(this)
        stateCurrent = state
    }

    init {
        animator.setStates(stateCurrent.properties, stateCurrent.properties)
        fab.animator = animator
        fab.onClick {
            toggleState()
        }
    }

    private fun toggleState() {
        setState(if (stateCurrent == stateCollapsed) stateExpanded else stateCollapsed)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}