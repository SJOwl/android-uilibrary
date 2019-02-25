package au.sjowl.lib.view.bottomnav.fluid

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import au.sjowl.lib.view.bottomnav.NavigationItem
import org.jetbrains.anko.dip

class FluidNavigationBar : LinearLayout {

    var items = listOf<NavigationItem>()
        set(value) {
            field = value
            value.forEach { fluidNavigationItem ->
                addView(FluidTabView(context).apply {
                    drawableId = fluidNavigationItem.drawableId
                    title = fluidNavigationItem.title
                    animationDuration = this@FluidNavigationBar.animationDuration
                    colorTintSelected = this@FluidNavigationBar.colorTintSelected
                    colorTintUnselected = this@FluidNavigationBar.colorTintUnselected
                    colorBubble = this@FluidNavigationBar.colorBubble
                    colorBg = this@FluidNavigationBar.colorBackground
                })
            }
            currentItemIndex = 0
        }

    var currentItemIndex = 0
        set(value) {
            field = value
            (children.toList()[value] as FluidTabView).checked = true
        }

    var colorBubble = Color.parseColor("#0011B6")
        set(value) {
            field = value
            children.forEach { (it as FluidTabView).colorBubble = value }
        }

    var colorBackground = Color.parseColor("#fafafa")
        set(value) {
            field = value
            children.forEach { (it as FluidTabView).colorBg = value }
        }

    var colorTintSelected = Color.parseColor("#fafafa")
        set(value) {
            field = value
            children.forEach { (it as FluidTabView).colorTintSelected = value }
        }

    var colorTintUnselected = colorBubble
        set(value) {
            field = value
            children.forEach { (it as FluidTabView).colorTintUnselected = value }
        }

    var colorTitle = Color.BLACK
        set(value) {
            field = value
            children.forEach { (it as FluidTabView).colorTitle = value }
        }

    var animationDuration: Long = 180L
        set(value) {
            field = value
            children.forEach { (it as FluidTabView).animationDuration = value }
        }

    var iconSize = context.dip(24)
        set(value) {
            field = value
            children.forEach { (it as FluidTabView).iconSize = value * 1f }
        }

    private var spaceBetweenIcons = 0

    private var onItemSelected: ((index: Int) -> Unit)? = null

    private var onItemReselected: ((index: Int) -> Unit)? = null

    private val ovalPadding = context.dip(30)

    private val heightDefault = context.dip(56)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = defaultSize(widthMeasureSpec)
        val h = defaultSize(heightMeasureSpec)
        setMeasuredDimension(w, h)

        if (items.size !in (3..5)) throw IllegalStateException("Bottombar must contain 3-5 items")
        spaceBetweenIcons = measuredWidth / items.size

        val childW = measuredWidth / items.size
        children.forEach {
            it.layoutParams.width = childW
        }

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

    fun onItemSelected(callback: ((index: Int) -> Unit)) {
        onItemSelected = callback
    }

    fun onItemReselected(callback: ((index: Int) -> Unit)) {
        onItemReselected = callback
    }

    private fun selectItem(index: Int) {
        children.forEachIndexed { i, view ->
            (view as FluidTabView).checked = index == i
        }
    }

    private inline fun indexOfItemClicked(x: Float): Int = x.toInt() / (measuredWidth / items.size)

    private fun defaultSize(measureSpec: Int, size: Int = heightDefault + ovalPadding): Int {
        var result = size
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

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