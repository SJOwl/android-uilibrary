package au.sjowl.lib.ui.views.checkbox

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import au.sjowl.lib.ui.views.utils.AnimatedPropertyF
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick

class BorderedCheckbox : View {

    var checked: Boolean = false
        set(value) {
            field = value
            anim.cancel()
            if (value) anim.start() else anim.reverse()
        }

    var animationDuration = 120L

    @ColorInt
    var color: Int = Color.GREEN
        set(value) {
            field = value
            paintBackground.color = value
        }

    var radiusPercent: Float = 0.09f

    private val animFloat = AnimatedPropertyF(0f, 0f, 1f)

    private val anim = valueAnim(animFloat)

    private var onCheckedChangedListener: ((checked: Boolean) -> Unit)? = null

    private val paintBackground = Paint().apply {
        isAntiAlias = true
        color = this@BorderedCheckbox.color
        style = Paint.Style.FILL
        strokeWidth = context.dip(3).toFloat()
    }

    private val paintTick = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = context.dip(4).toFloat()
        pathEffect = CornerPathEffect(2f)
        strokeCap = Paint.Cap.ROUND
    }

    private val pathTick = Path()

    private val rectBackground = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectBackground.bottom = h * 1f
        rectBackground.right = w * 1f
    }

    override fun onDraw(canvas: Canvas) {
        paintBackground.alpha = (animFloat.value * 255).toInt()
        paintBackground.style = Paint.Style.FILL
        canvas.drawRoundRect(rectBackground, rectBackground.width() * radiusPercent, rectBackground.height() * radiusPercent, paintBackground)
        paintBackground.alpha = 255
        paintBackground.style = Paint.Style.STROKE
        canvas.drawRoundRect(rectBackground, rectBackground.width() * radiusPercent, rectBackground.height() * radiusPercent, paintBackground)

        drawTick(canvas)
        super.onDraw(canvas)
    }

    override fun onDetachedFromWindow() {
        onCheckedChangedListener = null
        super.onDetachedFromWindow()
    }

    fun check() {
        checked = !checked
    }

    fun onCheckedChangedListener(listener: ((checked: Boolean) -> Unit)?) {
        onCheckedChangedListener = listener
    }

    private inline fun drawTick(canvas: Canvas) {
        paintTick.alpha = (animFloat.value * 255).toInt()
        pathTick.reset()
        pathTick.moveTo(0.77f * measuredWidth, 0.29f * measuredHeight)
        pathTick.lineTo(0.42f * measuredWidth, 0.75f * measuredHeight)
        pathTick.lineTo(0.25f * measuredWidth, 0.54f * measuredHeight)
        canvas.drawPath(pathTick, paintTick)
    }

    private fun valueAnim(animatedProperty: AnimatedPropertyF): ValueAnimator {
        return ValueAnimator.ofFloat(animatedProperty.from, animatedProperty.to).apply {
            duration = animationDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                animatedProperty.value = it.animatedValue as Float
                this@BorderedCheckbox.invalidate()
            }
        }
    }

    private fun init() {
        onClick {
            checked = !checked
            onCheckedChangedListener?.invoke(checked)
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