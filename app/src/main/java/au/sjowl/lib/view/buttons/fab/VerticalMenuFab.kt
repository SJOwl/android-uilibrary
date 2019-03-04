package au.sjowl.lib.view.buttons.fab

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.animations.ViewStateAnimator
import au.sjowl.lib.view.animations.interpolators.providers.BounceInterpolatorProvider
import au.sjowl.lib.view.buttons.fab.commons.FabMenuView
import au.sjowl.lib.view.buttons.fab.commons.FabView
import au.sjowl.lib.view.buttons.fab.commons.FmState
import au.sjowl.lib.view.buttons.fab.commons.FmStateCollapsed
import au.sjowl.lib.view.buttons.fab.commons.FmStateExpanded
import au.sjowl.lib.view.utils.gone
import au.sjowl.lib.view.utils.scale
import au.sjowl.lib.view.utils.show
import org.jetbrains.anko.dimen
import org.jetbrains.anko.sdk27.coroutines.onClick

class VerticalMenuFab : ViewGroup {

    val items = arrayListOf<FabMenuView>()

    var fabIconId = 0
        set(value) {
            field = value
            fab.icon = ContextCompat.getDrawable(context, value) as Drawable
        }

    val animator = ViewStateAnimator(180L, BounceInterpolatorProvider())

    private val marginItems = context.dimen(R.dimen.fab_menu_vertical_margins)

    private val fab = FabView(context)

    private val baseWidth = context.dimen(R.dimen.fab_menu_vertical_size)

    private val multiplier get() = animator.getFloat(FmState.MULT) // [0f;1f]

    private val stateCollapsed = FmStateCollapsed(context)

    private val stateExpanded = FmStateExpanded(context)

    private var stateCurrent: FmState = stateCollapsed

    private var onItemClickListener: ((Int) -> Unit)? = null

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val r = baseWidth / 2
        val cx = right - r - paddingRight
        var cy = bottom - r - paddingBottom
        fab.layout(cx - r, cy - r, cx + r, cy + r)
        fab.postInvalidate()
        fab.rotation = 315 * multiplier
        fab.scale(-0.3f * multiplier + 1)

        for ((index, it) in items.withIndex()) {
            if (multiplier > 0f) {
                it.show()
                cy = bottom - paddingBottom - r - ((2 * (index + 1) * r + marginItems * (index + 1)) * multiplier).toInt()
                it.layout(cx - r, cy - r, cx + r, cy + r)
                it.rotation = (1f - multiplier) * 180
                it.scale((multiplier + 1f) / 2)
            } else {
                it.gone()
            }
        }
    }

    fun setMenuItems(items: Collection<Int>) {
        this.items.clear()
        removeAllViews()

        items.forEachIndexed { index, drawableId ->
            val menuItem = FabMenuView(context).apply {
                icon = context.getDrawable(drawableId) as Drawable
                animator = this@VerticalMenuFab.animator
            }
            this.items.add(menuItem)
            addView(menuItem)
            menuItem.onClick {
                onItemClickListener?.invoke(index)
                setState(stateCollapsed)
            }
        }
        addView(fab)
        invalidate()
    }

    fun onItemSelected(function: (Int) -> Unit) {
        onItemClickListener = function
    }

    private fun setState(state: FmState) {
        if (state == stateCurrent) return
        animator.setStates(stateCurrent.properties, state.properties)
        animator.animate(this)
        stateCurrent = state
    }

    private fun toggleState() {
        setState(if (stateCurrent == stateCollapsed) stateExpanded else stateCollapsed)
    }

    init {
        animator.setStates(stateCurrent.properties, stateCurrent.properties)
        fab.animator = animator
        fab.onClick {
            toggleState()
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}