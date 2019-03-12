package au.sjowl.lib.view.buttons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.animations.ViewStateAnimator
import au.sjowl.lib.view.utils.colorCompat
import au.sjowl.lib.view.utils.contains
import org.jetbrains.anko.dip
import org.jetbrains.anko.textColor

class SimpleButton : TextView {

    private val COLOR_BACKGROUND = 1

    private val COLOR_TEXT = 2

    private val ELEVATION = 3

    private var baseElevation = context.dip(4) * 1f

    private val r = context.dip(20) * 1f

    private val stateDefault = mapOf(
        COLOR_BACKGROUND to context.colorCompat(R.color.button_background_default),
        COLOR_TEXT to context.colorCompat(R.color.button_text_default),
        ELEVATION to baseElevation
    )

    private val statePressed = mapOf(
        COLOR_BACKGROUND to context.colorCompat(R.color.button_background_pressed),
        COLOR_TEXT to context.colorCompat(R.color.button_text_pressed),
        ELEVATION to 0f
    )

    private val stateDisabled = mapOf(
        COLOR_BACKGROUND to context.colorCompat(R.color.button_background_disabled),
        COLOR_TEXT to context.colorCompat(R.color.button_text_disabled),
        ELEVATION to baseElevation
    )

    private var stateCurrent = stateDefault

    private val paintBackground = Paint().apply {
        color = stateDefault[COLOR_BACKGROUND] as Int
        style = Paint.Style.FILL
        strokeWidth = 10f
        isAntiAlias = true
    }

    private val rect = Rect(0, 0, width, height)

    private val animator = ViewStateAnimator(180L)

    private var colorBackground: Int = Color.WHITE
        set(value) {
            if (field != value) {
                paintBackground.color = value
                field = value
                postInvalidate()
            }
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rect.right = width
        rect.bottom = height
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(rect, r)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        paintBackground.color = animator.getColor(COLOR_BACKGROUND)
        textColor = animator.getColor(COLOR_TEXT)
        canvas.drawRoundRect(0f, 0f, width * 1f, height * 1f, r, r, paintBackground)
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isEnabled) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> setState(statePressed)
                MotionEvent.ACTION_MOVE -> if (contains(event)) setState(statePressed) else setState(stateDefault)
                MotionEvent.ACTION_UP -> {
                    if (contains(event)) {
                        callOnClick()
                    }
                    setState(stateDefault)
                }
                MotionEvent.ACTION_CANCEL -> setState(stateDefault)
            }
            return true
        }
        return false
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setState(if (enabled) stateDefault else stateDisabled)
    }

    private fun setState(state: Map<Int, Any>) {
        if (stateCurrent != state) {
            textColor = state[COLOR_TEXT] as Int
            elevation = state[ELEVATION] as Float
            colorBackground = state[COLOR_BACKGROUND] as Int

            animator.setStates(stateCurrent, state)
            stateCurrent = state
            animator.animate(this)
        }
    }

    private fun init() {
        animator.setStates(stateDefault, stateDefault)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
}