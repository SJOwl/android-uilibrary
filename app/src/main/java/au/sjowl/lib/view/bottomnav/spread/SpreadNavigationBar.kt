package au.sjowl.lib.view.bottomnav.spread

import android.content.Context
import android.graphics.Color
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import org.jetbrains.anko.dip

class SpreadNavigationBar : LinearLayout {

    var items = listOf<SpreadNavigationItem>()
        set(value) {
            field = value
            value.forEach { fluidNavigationItem ->
                addView(SpreadTabView(context).apply {
                    drawableId = fluidNavigationItem.drawableId
                    title = fluidNavigationItem.title
                    colorTintSelected = fluidNavigationItem.colorTextSelected
                    colorBubble = fluidNavigationItem.colorBackground
                    animationDuration = this@SpreadNavigationBar.animationDuration
                    colorTintUnselected = this@SpreadNavigationBar.colorTintUnselected
                })
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

    private var onItemSelected: ((index: Int) -> Unit)? = null

    private var onItemReselected: ((index: Int) -> Unit)? = null

    private val ovalPadding = context.dip(30)

    private val heightDefault = context.dip(56)

    private val transition = AutoTransition().apply {
        duration = animationDuration
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = defaultSize(widthMeasureSpec)
        val h = defaultSize(heightMeasureSpec)
        setMeasuredDimension(w, h)

        measureChildren()

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                val selectedIndex = indexOfItemClicked(event.x)
                selectItem(selectedIndex)
            }
            MotionEvent.ACTION_UP -> {
                val selectedIndex = indexOfItemClicked(event.x)
                if (currentItemIndex != selectedIndex) {
                    onItemSelected?.invoke(selectedIndex)
                    currentItemIndex = selectedIndex
                    selectItem(currentItemIndex)
                } else {
                    onItemReselected?.invoke(currentItemIndex)
                }
            }
        }
        return true
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

    private inline fun indexOfItemClicked(x: Float): Int {
        val list = children.toList()
        var child: View
        for (index in 0 until childCount) {
            child = list[index]
            if (child.x < x && child.width + child.x > x)
                return index
        }

        throw IllegalStateException("No tab clicked")
    }

    private fun defaultSize(measureSpec: Int, size: Int = heightDefault + ovalPadding): Int {
        var result = size
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        when (specMode) {
            MeasureSpec.AT_MOST -> result = size
            MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}