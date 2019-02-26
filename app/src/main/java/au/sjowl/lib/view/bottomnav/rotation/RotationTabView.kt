package au.sjowl.lib.view.bottomnav.rotation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import au.sjowl.lib.view.bottomnav.Badge
import au.sjowl.lib.view.bottomnav.Boundaries
import au.sjowl.lib.view.utils.AnimProperty
import au.sjowl.lib.view.utils.AnimatedPropertyF
import au.sjowl.lib.view.utils.AnimatedPropertyInt
import org.jetbrains.anko.dip

class RotationTabView : View {

    lateinit var drawable: Drawable

    var badge = Badge(context)

    var checked: Boolean = false
        set(value) {
            if (field != value) {
                if (isVisible) {
                    setSelectedStateAnimations()
                    field = value
                    animateSelectedState()
                } else {
                    field = value
                    setSelectedStateAnimations()
                }
            }
        }

    var drawableId: Int = 0
        set(value) {
            field = value
            drawable = ContextCompat.getDrawable(context, value)
                ?: throw IllegalArgumentException("No such drawable resource")
        }

    var title = ""
        set(value) {
            field = value.capitalize()
        }

    var colorTintSelected = Color.parseColor("#6ED37C")
        set(value) {
            field = value
            setSelectedStateAnimations()
        }

    var colorTintUnselected = Color.parseColor("#0011B6")
        set(value) {
            field = value
            setSelectedStateAnimations()
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

    var animationDuration: Long = 180L

    var iconSize = context.dip(24) * 1f
        set(value) {
            field = value
            defaultWidth = value * 2.8f
            iconHalf = value / 2
        }

    private var defaultWidth = iconSize * 2.8f

    private val animFloat = AnimatedPropertyF()

    private var animTint: AnimatedPropertyInt = AnimatedPropertyInt(colorTintUnselected, colorTintUnselected, colorTintSelected)

    private val animProperties = arrayListOf<AnimProperty>().apply {
        add(animFloat)
        add(animTint)
    }

    private val sb = Boundaries()

    private var iconHalf = iconSize / 2

    private var animatorSet: AnimatorSet? = AnimatorSet()

    private val paintOvalBg = defaultPaint().apply {
        color = colorBubble
        style = Paint.Style.STROKE
        strokeWidth = 10f
        textSize = context.dip(14).toFloat()
    }

    private val paintBg = defaultPaint().apply {
        color = colorBg
        style = Paint.Style.FILL
    }

    private val baseHeight = context.dip(56) * 1f + 1f

    private val radiusMax = baseHeight * 0.45f

    private val scaleAmplitude = 0.5

    private var centerX = 0

    private var centerY = (baseHeight / 2).toInt()

    private val animBadgeFloat = AnimatedPropertyF()

    private var badgeAnim: Animator? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = defaultSize(widthMeasureSpec, defaultWidth)
        val h = defaultSize(heightMeasureSpec, baseHeight)
        setMeasuredDimension(w, h)

        centerX = measuredWidth / 2
        centerY = (baseHeight / 2).toInt()

        setSelectedStateAnimations()
    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawBgCircle(canvas)
        drawIcon(canvas)
        drawBadge(canvas)
    }

    fun animateBadgeChange() {
        animBadgeFloat.from = 0f
        animBadgeFloat.to = 1f
        animBadgeFloat.setup()

        badgeAnim?.cancel()
        badgeAnim = valueAnim(animBadgeFloat)
        badgeAnim?.start()
    }

    private fun defaultPaint() = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        strokeWidth = 10f
        isAntiAlias = true
    }

    private inline fun drawBackground(canvas: Canvas) {
        canvas.drawRect(0f, measuredHeight - baseHeight, measuredWidth * 1f, measuredHeight * 1f, paintBg)
    }

    private inline fun drawBgCircle(canvas: Canvas) {
        sb.centerX = centerX * 1f
        sb.centerY = centerY * 1f
        sb.radius = animFloat.value * radiusMax

        if (animFloat.from < animFloat.to) {
            paintOvalBg.alpha = ((1 - animFloat.value) * 255).toInt()
            paintOvalBg.strokeWidth = 10f * (1 - animFloat.value)
            canvas.drawCircle(sb.centerX, sb.centerY, sb.radius, paintOvalBg)
        }
    }

    private inline fun drawIcon(canvas: Canvas) {
        sb.centerX = centerX * 1f
        sb.centerY = centerY * 1f
        sb.radius = (iconHalf * (1 + scaleAmplitude * Math.sin(animFloat.value * Math.PI))).toFloat()
        val r = (sb.radius * Math.abs(Math.cos(animFloat.value * Math.PI))).toInt()
        drawable.setBounds(centerX - r, sb.top, centerX + r, sb.bottom)
        drawable.setTint(animTint.value)
        drawable.draw(canvas)
    }

    private inline fun drawBadge(canvas: Canvas) {
        badge.draw(canvas, centerX + iconHalf, centerY - iconHalf, animFloat.value, animBadgeFloat.value)
    }

    private fun setSelectedStateAnimations() {
        animFloat.from = 0f
        animFloat.to = 1f

        animTint.from = colorTintUnselected
        animTint.to = colorTintSelected

        if (checked) animProperties.forEach {
            it.reverse()
        }

        animProperties.forEach { it.setup() }
    }

    private fun animateSelectedState() {
        animatorSet?.cancel()
        animatorSet = AnimatorSet().apply {
            playTogether(
                valueAnim(animFloat),
                colorAnim(animTint)
            )
            start()
        }
    }

    private fun valueAnim(animatedProperty: AnimatedPropertyF): ValueAnimator? {
        return ValueAnimator.ofFloat(animatedProperty.from, animatedProperty.to).apply {
            duration = animationDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                animatedProperty.value = it.animatedValue as Float
                this@RotationTabView.postInvalidate()
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

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}