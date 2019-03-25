package au.sjowl.lib.ui.views.bottomnav.spread

import android.content.Context
import android.graphics.Color
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick

class SpreadNavigationBar : ViewGroup {

    val childrenX = arrayListOf<View>()

    var items = listOf<SpreadNavigationItem>()
        set(value) {
            field = value
            value.forEachIndexed { selectedIndex, fluidNavigationItem ->
                val v = SpreadTabView(context).apply {
                    drawableId = fluidNavigationItem.drawableId
                    title = fluidNavigationItem.title
                    colorTintSelected = fluidNavigationItem.colorTextSelected
                    colorBubble = fluidNavigationItem.colorBackground
                    animationDuration = this@SpreadNavigationBar.animationDuration
                    colorTintUnselected = this@SpreadNavigationBar.colorTintUnselected
                    onClick {
                        if (currentItemIndex != selectedIndex) {
                            onItemSelected?.invoke(selectedIndex)
                            currentItemIndex = selectedIndex
                            selectItem(currentItemIndex)
                        } else {
                            onItemReselected?.invoke(currentItemIndex)
                        }
                    }
                }
                addView(v)
                childrenX.add(v)
            }
            currentItemIndex = 0
        }

    var currentItemIndex = 0
        set(value) {
            field = value
            selectItem(value)
        }

    var animationDuration: Long = 180L
        set(value) {
            field = value
            children.forEach { (it as SpreadTabView).animationDuration = value }
            transition.duration = value
        }

    var colorTintUnselected = Color.MAGENTA
        set(value) {
            field = value
            children.forEach { (it as SpreadTabView).colorTintUnselected = value }
        }

    var colorBadgeBackground: Int = Color.MAGENTA
        set(value) {
            field = value
            children.forEach { (it as SpreadTabView).badge.paintBadge.color = value }
        }

    var colorBadgeText: Int = Color.WHITE
        set(value) {
            field = value
            children.forEach { (it as SpreadTabView).badge.paintBadgeText.color = value }
        }

    var iconSize = context.dip(24)
        set(value) {
            field = value
            children.forEach { (it as SpreadTabView).iconSize = value * 1f }
        }

    private val baseHeight = context.dip(56) * 1f + 1f

    private var onItemSelected: ((index: Int) -> Unit)? = null

    private var onItemReselected: ((index: Int) -> Unit)? = null

    private val transition = AutoTransition().apply {
        duration = animationDuration
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren()
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), baseHeight.toInt())
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var w = 0
        children.forEach { view ->
            w = view.layoutParams.width
            view.layout(left, 0, left + w, measuredHeight)
            left += w
        }
    }

    fun setBadgeCount(tabIndex: Int, badgeCount: Int) {
        val tab = (children.toList()[tabIndex] as SpreadTabView)
        tab.badge.count = badgeCount
        tab.animateBadgeChange()
    }

    fun onItemSelected(callback: ((index: Int) -> Unit)) {
        onItemSelected = callback
    }

    fun onItemReselected(callback: ((index: Int) -> Unit)) {
        onItemReselected = callback
    }

    private fun measureChildren() {
        if (items.size !in (3..5)) throw IllegalStateException("Bottombar must contain 3-5 items")
        val childW = measuredWidth / (items.size + 1)
        children.forEachIndexed { index, view ->
            view.layoutParams.width = if (index == currentItemIndex) childW * 2 else childW
        }
    }

    private fun selectItem(index: Int) {
        children.forEachIndexed { i, view ->
            (view as SpreadTabView).checked = index == i
        }
        requestLayout()
        TransitionManager.beginDelayedTransition(this, transition)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}