package au.sjowl.lib.view.buttons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.animations.StateAnimator
import au.sjowl.lib.view.utils.colorCompat
import au.sjowl.lib.view.utils.drawTextCentered
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

class SubmitButton : View {

    var text = "Submit"

    var textError = "Error, retry?"

    var progress = 0f
        set(value) {
            if (value >= 1f) {
                setState(stateDone)
            }
            field = value
            postInvalidate()
        }

    var animDuration = 1800L
        set(value) {
            field = value
            animator.animationDuration = value
        }

    private val COLOR_BACKGROUND = 1

    private val COLOR_FRAME = 2

    private val COLOR_TEXT = 3

    private val MULT = 4

    private val r = context.dip(40) * 1f

    private val mainColor = context.colorCompat(R.color.color2_darker)

    private val stateDisabled = mapOf(
        MULT to 0f,
        COLOR_BACKGROUND to Color.WHITE,
        COLOR_FRAME to mainColor,
        COLOR_TEXT to mainColor
    )

    private val stateReady = mapOf(
        MULT to 1f,
        COLOR_BACKGROUND to mainColor,
        COLOR_FRAME to mainColor,
        COLOR_TEXT to Color.WHITE
    )

    private val stateProgress = mapOf(
        MULT to 0f,
        COLOR_BACKGROUND to context.colorCompat(R.color.color2),
        COLOR_FRAME to context.colorCompat(R.color.color2),
        COLOR_TEXT to Color.WHITE
    )

    private val stateDone = mapOf(
        MULT to 1f,
        COLOR_BACKGROUND to mainColor,
        COLOR_FRAME to mainColor,
        COLOR_TEXT to Color.WHITE
    )

    private val stateError = mapOf(
        MULT to 1f,
        COLOR_BACKGROUND to context.colorCompat(R.color.color5_darker),
        COLOR_FRAME to mainColor,
        COLOR_TEXT to Color.WHITE
    )

    private var stateCurrent = stateDisabled

    private val paintBackground = Paint().apply {
        color = stateCurrent[COLOR_BACKGROUND] as Int
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val paintFrame = Paint().apply {
        color = stateCurrent[COLOR_FRAME] as Int
        style = Paint.Style.STROKE
        strokeWidth = 10f
        isAntiAlias = true
    }

    private val paintText = Paint().apply {
        color = stateCurrent[COLOR_TEXT] as Int
        textSize = context.sp(20) * 1f
        isAntiAlias = true
    }

    private val rect = Rect(0, 0, width, height)

    private val animator = StateAnimator(1800L)

    private val rectText = Rect()

    private val doneSvg = ContextCompat.getDrawable(context, R.drawable.ic_ok)
        ?: throw IllegalStateException("")

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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        paintText.getTextBounds(text, 0, text.length, rectText)

        val w = defaultSize(widthMeasureSpec, (rectText.width() + paddingLeft + paddingRight).toFloat())
        val h = defaultSize(heightMeasureSpec, (rectText.height() + paddingTop + paddingBottom).toFloat())
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {

        println("states $stateDisabled, $stateReady, $stateProgress, $stateDone, $stateError")

        paintText.color = animator.getColor(COLOR_TEXT)
        paintBackground.color = animator.getColor(COLOR_BACKGROUND)
        paintFrame.color = animator.getColor(COLOR_FRAME)

        when {
            stateCurrent === stateDisabled || stateCurrent === stateReady -> {
                canvas.run {
                    val sw = paintFrame.strokeWidth / 2
                    val left = sw
                    val top = sw
                    val right = width * 1f - sw
                    val bottom = height * 1f - sw
                    val centerX = width / 2f
                    val centerY = height / 2f
                    drawRoundRect(left, top, right, bottom, r, r, paintBackground)
                    drawRoundRect(left, top, right, bottom, r, r, paintFrame)
                    drawTextCentered(text, centerX, centerY, paintText, rectText)
                }
            }
            stateCurrent === stateProgress -> {

                val sw = paintFrame.strokeWidth
                val p = height * 1f - sw
                var w = width * animator.getFloat(MULT)
                if (p < w) {
                    canvas.drawRoundRect((width - w) / 2, sw / 2, (width + w) / 2, height * 1f - sw / 2, r, r, paintBackground)
                } else {
                    w = p
                    val left = (width - w) / 2
                    val top = sw / 2
                    val right = (width + w) / 2
                    val bottom = height * 1f - sw / 2
                    val angle = progress * 360
                    paintBackground.alpha = (255 * animator.getFloat(MULT)).toInt()
                    canvas.run {
                        drawRoundRect(left, top, right, bottom, r, r, paintBackground)
                        drawArc(left, top, right, bottom, -90f, angle, false, paintFrame)
                    }
                }
            }
            stateCurrent === stateDone -> {
                paintBackground.color = stateDone[COLOR_BACKGROUND] as Int
                paintFrame.color = stateDone[COLOR_FRAME] as Int
                val sw = paintFrame.strokeWidth
                val p = height * 1f - sw
                var w = width * animator.getFloat(MULT)
                println("width = $w, mult = ${animator.getFloat(MULT)}")
                w = Math.max(p, w)
                paintBackground.alpha = (255 * animator.getFloat(MULT)).toInt()
                canvas.run {
                    drawRoundRect((width - w) / 2, sw / 2, (width + w) / 2, height * 1f - sw / 2, r, r, paintBackground)
                    val svgw = doneSvg.intrinsicWidth
                    val cx = width / 2
                    val cy = height / 2
                    val m = (svgw * animator.getFloat(MULT)).toInt()
                    doneSvg.run {
                        setBounds(cx - m, cy - svgw, cx + m, cy + svgw)
                        setTint(Color.WHITE)
                        alpha = (255 * animator.getFloat(MULT)).toInt()
                        draw(canvas)
                    }
                }
            }
            stateCurrent === stateError -> {
                paintBackground.color = stateError[COLOR_BACKGROUND] as Int
                paintFrame.color = stateError[COLOR_FRAME] as Int
                val sw = paintFrame.strokeWidth
                val p = height * 1f - sw
                var w = width * animator.getFloat(MULT)
                println("width = $w, mult = ${animator.getFloat(MULT)}")
                w = Math.max(p, w)
                paintBackground.alpha = (255 * animator.getFloat(MULT)).toInt()
                canvas.run {
                    drawRoundRect((width - w) / 2, sw / 2, (width + w) / 2, height * 1f - sw / 2, r, r, paintBackground)
                    drawTextCentered(textError, width / 2f, height / 2f - sw, paintText, rectText)
                }
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setState(if (enabled) stateReady else stateDisabled)
    }

    fun setState(state: Int) {
        when (state) {
            STATE_DISABLED -> {
                setState(stateDisabled)
                isEnabled = false
            }
            STATE_READY -> setState(stateReady)
            STATE_PROGRESS -> setState(stateProgress)
            STATE_DONE -> setState(stateDone)
            STATE_ERROR -> setState(stateError)
        }
    }

    fun showProgress(currentProgress: Float) {
        progress = currentProgress
        setState(stateProgress)
    }

    private fun setState(state: Map<Int, Any>) {
        if (stateCurrent === state) return

        animator.setStates(stateCurrent, state)
        animator.animate(this)
        stateCurrent = state
    }

    private fun init() {
        animator.setStates(stateCurrent, stateCurrent)
    }

    private fun defaultSize(measureSpec: Int, size: Float): Int {
        var result = size
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec) * 1f

        when (specMode) {
            View.MeasureSpec.AT_MOST -> result = size
            View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.EXACTLY -> result = specSize
        }
        return result.toInt()
    }

    companion object {
        const val STATE_READY = 0
        const val STATE_PROGRESS = 1
        const val STATE_DONE = 2
        const val STATE_ERROR = 3
        const val STATE_DISABLED = 4
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