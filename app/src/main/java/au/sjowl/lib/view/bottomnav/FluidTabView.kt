package au.sjowl.lib.view.bottomnav

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import org.jetbrains.anko.dip

class FluidTabView : View {

    var drawable: Drawable? = null

    var checked: Boolean = false
        set(value) {
            if (field != value) {
                if (isVisible) {
                    setAnims()
                    field = value
                    anim()
                } else {
                    field = value
                    setAnims()
                }
            }
        }

    var drawableId: Int = 0
        set(value) {
            field = value
            drawable = ContextCompat.getDrawable(context, value)
        }

    var title = ""
        set(value) {
            field = value.capitalize()
        }

    var colorTintSelected = Color.parseColor("#fafafa")
        set(value) {
            field = value
            setAnims()
        }

    var colorTintUnselected = Color.parseColor("#0011B6")
        set(value) {
            field = value
            setAnims()
        }

    var colorBubble = Color.parseColor("#0011B6")
        set(value) {
            field = value
            paintOvalBg.color = value
        }

    var colorBg = Color.parseColor("#fafafa")
        set(value) {
            field = value
            paintBg.color = value
        }

    var colorTitle = Color.BLACK
        set(value) {
            field = value
            textPaint.color = value
        }

    var animationDuration: Long = 180L

    val iconSize = context.dip(32) * 1f

    val defaultWidth = iconSize * 2.8f

    private var animTranslation: AnimatedPropertyF = AnimatedPropertyF()

    private var animRadius: AnimatedPropertyF = AnimatedPropertyF()

    private var animAlpha: AnimatedPropertyF = AnimatedPropertyF()

    private val animProperties = arrayListOf<AnimProperty>()

    private var animTint: AnimatedPropertyInt = AnimatedPropertyInt(colorTintUnselected, colorTintUnselected, colorTintSelected)

    private val sb = Boundaries()

    private var centerX = 0

    private var centerY = 0

    private val iconHalf = iconSize / 2

    private var animatorSet: AnimatorSet? = AnimatorSet()

    private val paintOvalBg = Paint().apply {
        color = colorBubble
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val paintBg = Paint().apply {
        color = colorBg
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = colorTitle
        isAntiAlias = true
        textSize = context.dip(14).toFloat()
    }

    private val path = Path()

    private val placeholderRect = Rect()

    private val baseHeight = context.dip(56) * 1f + 1f

    private val radiusMax = iconSize * 0.7f

    private val coverWidth = radiusMax * 0.2f

    private val topMargin = radiusMax - coverWidth

    private val translationMax = radiusMax + coverWidth

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = defaultSize(widthMeasureSpec, defaultWidth)
        val h = defaultSize(heightMeasureSpec, baseHeight + topMargin)
        setMeasuredDimension(w, h)

        centerX = measuredWidth / 2
        centerY = (topMargin + baseHeight / 2).toInt()

        textPaint.getTextBounds(title, 0, title.length, placeholderRect)
        setAnims()
    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawBezierPath(canvas)
        drawBgCircle(canvas)
        drawIcon(canvas)
        drawTitle(canvas)
    }

    private inline fun drawBackground(canvas: Canvas) {
        canvas.drawRect(0f, measuredHeight - baseHeight, measuredWidth * 1f, measuredHeight * 1f, paintBg)
    }

    private inline fun drawBezierPath(canvas: Canvas) {
        path.reset()
        val r = animRadius.value + coverWidth
        val origY = animTranslation.value - r
        if (origY < topMargin) {
            val xb = r * 0.2f
            val x = 0.75f * r
            val rb = animRadius.value + coverWidth * 3
            path.moveTo(centerX - rb, topMargin)
            path.cubicTo(
                centerX - r + xb, topMargin,
                centerX - x, origY,
                centerX * 1f, origY
            )
            path.cubicTo(
                centerX + x, origY,
                centerX + r - xb, topMargin,
                centerX + rb, topMargin
            )
            canvas.drawPath(path, paintBg)
        }
    }

    private inline fun drawBgCircle(canvas: Canvas) {
        sb.centerX = centerX * 1f
        sb.centerY = animTranslation.value
        sb.radius = animRadius.value

        canvas.drawCircle(sb.centerX, sb.centerY, sb.radius, paintOvalBg)
    }

    private inline fun drawIcon(canvas: Canvas) {
        sb.radius = iconHalf
        drawable?.setBounds(sb.left, sb.top, sb.right, sb.bottom)
        drawable?.setTint(animTint.value)
        drawable?.draw(canvas)
    }

    private inline fun drawTitle(canvas: Canvas) {
        val y = 0.5f * (measuredHeight + animTranslation.value + iconHalf)
        if (y < measuredHeight) {
            textPaint.alpha = (animAlpha.value * 255).toInt()
            canvas.drawTextCentered(title, centerX * 1f, y, textPaint, placeholderRect)
        }
    }

    private fun setAnims() {
        animTranslation.from = centerY * 1f
        animTranslation.to = if (title.isNotEmpty()) translationMax else animTranslation.from

        animRadius.from = 0f
        animRadius.to = radiusMax

        animTint.from = colorTintUnselected
        animTint.to = colorTintSelected

        animAlpha.from = 0f
        animAlpha.to = 1f

        if (checked) animProperties.forEach {
            it.reverse()
        }

        animProperties.forEach { it.setup() }
    }

    private fun anim() {
        animatorSet?.cancel()
        animatorSet = AnimatorSet().apply {
            playTogether(
                valueAnim(animTranslation),
                valueAnim(animRadius),
                colorAnim(animTint),
                valueAnim(animAlpha)
            )
            start()
        }
        animatorSet?.doOnEnd {
        }
    }

    private fun valueAnim(animatedProperty: AnimatedPropertyF): ValueAnimator? {
        return ValueAnimator.ofFloat(animatedProperty.from, animatedProperty.to).apply {
            duration = animationDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                animatedProperty.value = it.animatedValue as Float
                this@FluidTabView.postInvalidate()
            }
        }
    }

    private fun colorAnim(animatedProperty: AnimatedPropertyInt) = ValueAnimator().apply {
        addUpdateListener {
            animatedProperty.value = it.animatedValue as Int
        }
        setIntValues(animatedProperty.from, animatedProperty.to)
        setEvaluator(ArgbEvaluator())
        duration = animationDuration
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

    init {
        with(animProperties) {
            add(animTranslation)
            add(animRadius)
            add(animTint)
            add(animAlpha)
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}

interface AnimProperty {
    fun setup()
    fun reverse()
}

class AnimatedPropertyF(
    var value: Float = 0f,
    var from: Float = 0f,
    var to: Float = 0f
) : AnimProperty {
    override fun setup() {
        value = from
    }

    override fun reverse() {
        val t = from
        from = to
        to = t
    }
}

class AnimatedPropertyInt(
    var value: Int = 0,
    var from: Int = 0,
    var to: Int = 0
) : AnimProperty {
    override fun setup() {
        value = from
    }

    override fun reverse() {
        val t = from
        from = to
        to = t
    }
}

fun Canvas.drawTextCenteredVertically(text: String, x: Float, y: Float, paint: Paint, r: Rect) {
    paint.getTextBounds(text, 0, text.length, r)
    drawText(text, x, y + r.height() / 2, paint)
}

fun Canvas.drawTextCentered(text: String, x: Float, y: Float, paint: Paint, r: Rect) {
    paint.getTextBounds(text, 0, text.length, r)
    drawText(text, x - r.width() / 2, y + r.height() / 2, paint)
}