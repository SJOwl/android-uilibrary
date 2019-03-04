package au.sjowl.lib.view.buttons.fab.commons

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.animations.ViewStateAnimator
import au.sjowl.lib.view.animations.interpolators.providers.DefaultInterpolatorProvider
import org.jetbrains.anko.dimen
import org.jetbrains.anko.sdk27.coroutines.onClick

abstract class BaseMenuFab : ViewGroup {
    val items = arrayListOf<FabMenuView>()

    var fabIconId = 0
        set(value) {
            field = value
            fab.icon = ContextCompat.getDrawable(context, value) as Drawable
        }

    open val animator = ViewStateAnimator(180L, DefaultInterpolatorProvider())

    protected val fab = FabView(context)

    protected val baseWidth = context.dimen(R.dimen.fab_menu_vertical_size)

    private val stateCollapsed = FmStateCollapsed(context)

    private val stateExpanded = FmStateExpanded(context)

    private var stateCurrent: FmState = stateCollapsed

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setMenuItems(items: Collection<Int>) {
        this.items.clear()
        removeAllViews()

        items.forEachIndexed { index, drawableId ->
            val menuItem = FabMenuView(context).apply {
                icon = context.getDrawable(drawableId) as Drawable
                animator = this@BaseMenuFab.animator
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