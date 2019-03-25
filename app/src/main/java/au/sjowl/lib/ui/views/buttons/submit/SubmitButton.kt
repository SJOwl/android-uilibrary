package au.sjowl.lib.ui.views.buttons.submit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Outline
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import au.sjowl.lib.ui.views.buttons.submit.states.ButtonState
import au.sjowl.lib.ui.views.buttons.submit.states.ButtonState.Companion.COLOR_BACKGROUND
import au.sjowl.lib.ui.views.buttons.submit.states.ButtonState.Companion.COLOR_FRAME
import au.sjowl.lib.ui.views.buttons.submit.states.ButtonState.Companion.COLOR_TEXT
import au.sjowl.lib.ui.views.buttons.submit.states.StateDisabled
import au.sjowl.lib.ui.views.buttons.submit.states.StateDone
import au.sjowl.lib.ui.views.buttons.submit.states.StateError
import au.sjowl.lib.ui.views.buttons.submit.states.StateProgress
import au.sjowl.lib.ui.views.buttons.submit.states.StateReady
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.wrapContent

class SubmitButton : View {

    var text = "Submit"

    var textError = "Error, retry?"

    var progress = 0f
        set(value) {
            setState(stateProgress)
            if (value >= 1f) {
                setState(stateDone)
            }
            field = value
            postInvalidate()
        }

    var animationDuration = 1800L
        set(value) {
            field = value
            dh.animator.animationDuration = value
        }

    private val dh = DrawHelper(this)

    private val stateDisabled = StateDisabled(dh)

    private val stateReady = StateReady(dh)

    private val stateProgress = StateProgress(dh)

    private val stateDone = StateDone(dh)

    private val stateError = StateError(dh)

    private var stateCurrent: ButtonState = stateDisabled

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        dh.rect.right = width
        dh.rect.bottom = height
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setRoundRect(dh.rect, dh.r)
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        dh.paintText.getTextBounds(text, 0, text.length, dh.rectText)
        val t = dh.rectText.width()
        dh.paintText.getTextBounds(textError, 0, textError.length, dh.rectText)
        val t2 = dh.rectText.width()

        val w = defaultSize(widthMeasureSpec, (Math.max(t, t2) + paddingLeft + paddingRight).toFloat())
        val h = defaultSize(wrapContent, (dh.rectText.height() + paddingTop + paddingBottom).toFloat())
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        dh.paintText.color = dh.animator.getColor(COLOR_TEXT)
        dh.paintBackground.color = dh.animator.getColor(COLOR_BACKGROUND)
        dh.paintFrame.color = dh.animator.getColor(COLOR_FRAME)

        stateCurrent.draw(canvas)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        setState(if (enabled) stateReady else stateDisabled)
    }

    fun onSubmitClick(listener: (() -> Unit)?) {
        stateReady.onClickListener = listener
    }

    fun onDoneClick(listener: (() -> Unit)?) {
        stateDone.onClickListener = listener
    }

    fun onRetryClick(listener: (() -> Unit)?) {
        stateError.onClickListener = listener
    }

    fun onCancelClick(listener: (() -> Unit)?) {
        stateProgress.onClickListener = listener
    }

    fun showError() {
        setState(stateError)
    }

    fun showSubmit() {
        isEnabled = true
    }

    private fun setState(state: ButtonState) {
        if (stateCurrent == state) return

        dh.animator.setStates(stateCurrent.properties, state.properties)
        dh.animator.animate(this)
        stateCurrent = state
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

    private fun init() {
        dh.animator.setStates(stateCurrent.properties, stateCurrent.properties)
        onClick {
            stateCurrent.onClickListener?.invoke()
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
}