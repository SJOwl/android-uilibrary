package au.sjowl.app.widgets

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
        strokeWidth = context.dip(3).toFloat()
        pathEffect = CornerPathEffect(2f)
        strokeCap = Paint.Cap.ROUND
    }

    private val pathTick = Path()

    private val rectBackground = RectF()

    private val pointsTick = floatArrayOf(
        0.77f, 0.29f,
        0.42f, 0.75f,
        0.25f, 0.54f
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectBackground.left = paddingLeft * 1f
        rectBackground.top = paddingTop * 1f
        rectBackground.bottom = h * 1f - paddingBottom
        rectBackground.right = w * 1f - paddingRight
    }

    // todo create defaultSize
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        val w = defaultSize(widthMeasureSpec, dip(24))
//        val h = defaultSize(heightMeasureSpec, dip(24))
//        setMeasuredDimension(w, h)
//    }

    override fun onDraw(canvas: Canvas) {
        paintBackground.alpha = (animFloat.value * 255).toInt()
        paintBackground.style = Paint.Style.FILL
        canvas.drawRoundRect(rectBackground, rectBackground.width() * radiusPercent, rectBackground.height() * radiusPercent, paintBackground)
        paintBackground.alpha = 255
        paintBackground.style = Paint.Style.STROKE
        canvas.drawRoundRect(rectBackground, rectBackground.width() * radiusPercent, rectBackground.height() * radiusPercent, paintBackground)

        drawTick(canvas)
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
        drawPath(pathTick, pointsTick, canvas)
    }

    private fun drawPath(path: Path, points: FloatArray, canvas: Canvas) {
        path.reset()
        val dy = (animFloat.value - 1) * measuredHeight + paddingTop
        val multX = rectBackground.width()
        val multY = rectBackground.height()
        val dx = paddingLeft
        path.moveTo(points[0] * multX + dx, points[1] * multY + dy)
        for (i in 2 until points.size step 2) {
            pathTick.lineTo(points[i] * multX + dx, points[i + 1] * multY + dy)
        }
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