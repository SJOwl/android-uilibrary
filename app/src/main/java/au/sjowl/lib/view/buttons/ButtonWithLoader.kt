package au.sjowl.lib.view.buttons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.dip

/**
 * todo
 * enabled state: different from pressed and default, can not be changed
 * animate transitions between states
 * click speed can not be more 300ms?
 * learn about button possible states
 */
class ButtonWithLoader : TextView {

    private var baseElevation = context.dip(8) * 1f

    private val r = context.dip(8) * 1f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> setPressed()
            MotionEvent.ACTION_MOVE -> if (contains(event)) setPressed() else setDefault()
            MotionEvent.ACTION_UP -> {
                if (contains(event)) {
                    callOnClick()
                }
                setDefault()
            }
            MotionEvent.ACTION_CANCEL -> setDefault()
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.MAGENTA)
        super.onDraw(canvas)
    }

    private fun setPressed() {
//        backgroundDrawable = context.getDrawable(R.drawable.button_bg_pressed)
        elevation = 0f
    }

    private fun setDefault() {
//        backgroundDrawable = context.getDrawable(R.drawable.button_bg_default)

        val roundRectShape = RoundRectShape(floatArrayOf(10f, 10f, 10f, 10f, 10f, 10f, 10f, 10f), null, null)
        val shapeDrawable = ShapeDrawable(roundRectShape)
        shapeDrawable.paint.color = Color.WHITE
        backgroundDrawable = shapeDrawable

        elevation = baseElevation
    }

    constructor(context: Context) : super(context) {
        setDefault()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setDefault()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setDefault()
    }
}

inline fun View.contains(px: Int, py: Int): Boolean {
    return px > x && px < x + measuredWidth && py > y && py < y + measuredHeight
}

inline fun View.contains(px: Float, py: Float): Boolean {
    return px > x && px < x + width && py > y && py < y + height
}

inline fun View.contains(event: MotionEvent): Boolean {
    return contains(event.x + x, event.y + y)
}