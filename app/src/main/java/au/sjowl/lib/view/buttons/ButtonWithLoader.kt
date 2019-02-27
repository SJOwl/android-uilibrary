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
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.utils.colorCompat
import au.sjowl.lib.view.utils.contains
import org.jetbrains.anko.dip
import org.jetbrains.anko.textColor

/**
 * todo
 * enabled state: different from pressed and default, can not be changed
 * animate transitions between states
 * click speed can not be more 300ms?
 * learn about button possible states
 */
class ButtonWithLoader : TextView {

    private var baseElevation = context.dip(4) * 1f

    private val r = context.dip(20) * 1f

    private val stateDefault = InteractionState(context.colorCompat(R.color.button_background_default), context.colorCompat(R.color.button_text_default), baseElevation)

    private val statePressed = InteractionState(context.colorCompat(R.color.button_background_pressed), context.colorCompat(R.color.button_text_pressed), 0f)

    private val stateDisabled = InteractionState(context.colorCompat(R.color.button_background_disabled), context.colorCompat(R.color.button_text_disabled), baseElevation)

    private var stateCurrent = stateDefault

    private val paintBackground = Paint().apply {
        color = stateDefault.colorBackground
        style = Paint.Style.FILL
        strokeWidth = 10f
        isAntiAlias = true
    }

    private val rect = Rect(0, 0, width, height)

    private val animator = StateAnimator()

    private var colorBackground: Int = Color.WHITE
        set(value) {
            if (field != value) {
                paintBackground.color = value
                field = value
                postInvalidate()
            }
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

    override fun onDraw(canvas: Canvas) {
        paintBackground.color = animator.colorBackground.value
        textColor = animator.colorText.value
        canvas.drawRoundRect(0f, 0f, width * 1f, height * 1f, r, r, paintBackground)
        super.onDraw(canvas)
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

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setState(if (enabled) stateDefault else stateDisabled)
    }

    private fun setState(state: InteractionState) {
        if (stateCurrent != state) {
            textColor = state.colorText
            elevation = state.elevation
            colorBackground = state.colorBackground

            animator.setAnimations(stateCurrent, state)
            stateCurrent = state
            animator.animateSelectedState(this)
        }
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

    private fun init() {
        stateDefault
        animator.setAnimations(stateDefault, stateDefault)
    }
}