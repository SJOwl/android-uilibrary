package au.sjowl.lib.view.bottomnav

import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import org.jetbrains.anko.dip

class FluidNavigationBar : LinearLayout {

    var items = listOf<FluidNavigationItem>()
        set(value) {
            field = value
            value.forEach {
                val f = FluidNavigationDrawable(
                    it.title.capitalize(),
                    ContextCompat.getDrawable(context, it.drawableId)
                        ?: throw IllegalArgumentException("no such drawable: ${it.drawableId}")
                )
                drawableItems.add(f)
                addView(FluidTabView(context).apply {
                    drawable = f.drawable
                    title = f.title
                })
            }
            currentItemIndex = 0
        }

    var currentItemIndex = 0
        set(value) {
            field = value
            (children.toList()[value] as FluidTabView).checked = true
            postInvalidate()
        }

    var animationDuration: Long = 3000L

    var tintSelected = Color.parseColor("#fafafa")

    var tintUnselected = Color.parseColor("#000000")

    private val paintOvalBg = Paint().apply {
        color = Color.parseColor("#0011B6")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val paintBg = Paint().apply {
        color = Color.parseColor("#fafafa")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val colorBackground = Color.parseColor("#FDFDFD")

    private var drawableItems = arrayListOf<FluidNavigationDrawable>()

    private val iconSize = context.dip(24)

    private val radiusPadding = context.dip(12)

    private val iconHalf = iconSize / 2

    private val selectedIconSize = context.dip(40)

    private val selectedIconHalf = selectedIconSize / 2

    private var selectedIconHalfCurrent = selectedIconSize / 2
        set(value) {
            field = value
            postInvalidateOnAnimation()
        }

    private var prevSelectedHalf = 0

    private var spaceBetweenIcons = 0

    private var onItemSelected: ((index: Int) -> Unit)? = null

    private var onItemReselected: ((index: Int) -> Unit)? = null

    private var animatorSet: AnimatorSet? = AnimatorSet()

    private val ovalPadding = context.dip(30)

    private val heightDefault = context.dip(56)

    private val sb = Boundaries()

    private val psb = Boundaries()

    private val ub = Boundaries()

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
        if (event.action == MotionEvent.ACTION_UP) {
            val selectedIndex = indexOfItemClicked(event.x)
            if (currentItemIndex != selectedIndex) {
                onItemSelected?.invoke(selectedIndex)
                currentItemIndex = selectedIndex
                selectItem(currentItemIndex)
            } else {
                onItemReselected?.invoke(currentItemIndex)
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

class Boundaries(
    var centerX: Int = 0,
    var centerY: Int = 0,
    var radius: Int = 0
) {

    val rect: Rect
        get() {
            r.left = centerX - radius
            r.right = centerX + radius
            r.top = centerY - radius
            r.bottom = centerY + radius
            return r
        }

    val rectf: RectF
        get() {
            rf.left = (centerX - radius).toFloat()
            rf.right = (centerX + radius).toFloat()
            rf.top = (centerY - radius).toFloat()
            rf.bottom = (centerY + radius).toFloat()
            return rf
        }

    val left get() = centerX - radius

    val right get() = centerX + radius

    val top get() = centerY - radius

    val bottom get() = centerY + radius

    private val r = Rect()

    private val rf = RectF()
}

class FluidNavigationItem(
    val title: String,
    val drawableId: Int
)

private class FluidNavigationDrawable(
    val title: String,
    val drawable: Drawable
)

inline fun Canvas.drawOval(left: Int, top: Int, right: Int, bottom: Int, paint: Paint) {
    drawOval(left * 1f, top * 1f, right * 1f, bottom * 1f, paint)
}