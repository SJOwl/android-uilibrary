package au.sjowl.lib.ui.views.bottomnav.rotation

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick

class RotationNavigationBar : ViewGroup {

    var items = listOf<RotationNavItem>()
        set(value) {
            field = value
            value.forEachIndexed { selectedIndex, navItem ->
                addView(RotationTabView(context).apply {
                    drawableId = navItem.drawableId
                    title = navItem.title
                    colorTintSelected = navItem.colorSelected
                    animationDuration = this@RotationNavigationBar.animationDuration
                    colorTintUnselected = this@RotationNavigationBar.colorTintUnselected
                    colorBubble = this@RotationNavigationBar.colorBubble
                    onClick {
                        if (currentItemIndex != selectedIndex) {
                            onItemSelected?.invoke(selectedIndex)
                            currentItemIndex = selectedIndex
                            selectItem(currentItemIndex)
                        } else {
                            onItemReselected?.invoke(currentItemIndex)
                        }
                    }
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
            children.forEach { (it as RotationTabView).animationDuration = value }
        }

    var colorBubble = Color.parseColor("#0011B6")
        set(value) {
            field = value
            children.forEach { (it as RotationTabView).colorBubble = value }
        }

    var colorTintUnselected = Color.parseColor("#aaaaaa")
        set(value) {
            field = value
            children.forEach { (it as RotationTabView).colorTintUnselected = value }
        }

    var colorBadgeBackground: Int = colorBubble
        set(value) {
            field = value
            children.forEach { (it as RotationTabView).badge.paintBadge.color = value }
        }

    var colorBadgeText: Int = Color.WHITE
        set(value) {
            field = value
            children.forEach { (it as RotationTabView).badge.paintBadgeText.color = value }
        }

    var iconSize = context.dip(24)
        set(value) {
            field = value
            children.forEach { (it as RotationTabView).iconSize = value * 1f }
        }

    private var spaceBetweenIcons = 0

    private var onItemSelected: ((index: Int) -> Unit)? = null

    private var onItemReselected: ((index: Int) -> Unit)? = null

    private val heightDefault = context.dip(56)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (items.size !in (3..5)) throw IllegalStateException("Bottombar must contain 3-5 items")
        spaceBetweenIcons = measuredWidth / items.size

        val childW = measuredWidth / items.size
        children.forEach {
            it.layoutParams.width = childW
            it.layoutParams.height = heightDefault
            it.measure(childW, heightDefault)
        }

        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), heightDefault)
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
        val tab = (children.toList()[tabIndex] as RotationTabView)
        tab.badge.count = badgeCount
        tab.animateBadgeChange()
    }

    fun onItemSelected(callback: ((index: Int) -> Unit)) {
        onItemSelected = callback
    }

    fun onItemReselected(callback: ((index: Int) -> Unit)) {
        onItemReselected = callback
    }

    private fun selectItem(index: Int) {
        children.forEachIndexed { i, view ->
            (view as RotationTabView).checked = index == i
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}