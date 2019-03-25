package au.sjowl.lib.ui.views.bottomnav.spread

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import au.sjowl.lib.ui.views.bottomnav.Badge
import au.sjowl.lib.ui.views.bottomnav.Boundaries
import au.sjowl.lib.ui.views.utils.AnimProperty
import au.sjowl.lib.ui.views.utils.AnimatedPropertyF
import au.sjowl.lib.ui.views.utils.AnimatedPropertyInt
import org.jetbrains.anko.dip

class SpreadTabView : View {

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
            paintText.color = value
        }

    var colorTintUnselected = Color.parseColor("#0011B6")
        set(value) {
            field = value
            setSelectedStateAnimations()
            animTint.value = if (checked) colorTintSelected else colorTintUnselected
        }

    var colorBubble = Color.parseColor("#0011B6")
        set(value) {
            field = value
            paintOvalBg.color = value
        }

    var animationDuration: Long = 180L

    var iconSize = context.dip(24) * 1f
        set(value) {
            field = value
            defaultWidth = value * 2.8f
            iconHalf = value / 2
        }

    var defaultWidth = iconSize * 2.8f

    private val animFloat = AnimatedPropertyF()

    private var animTint: AnimatedPropertyInt = AnimatedPropertyInt(colorTintUnselected, colorTintUnselected, colorTintSelected)

    private val animProperties = arrayListOf<AnimProperty>().apply {
        add(animFloat)
        add(animTint)
    }

    private val sb = Boundaries()

    private var iconHalf = iconSize / 2

    private var animatorSet: AnimatorSet = AnimatorSet()

    private val paintOvalBg = defaultPaint().apply {
        color = colorBubble
        style = Paint.Style.FILL
    }

    private val paintTextBitmap = defaultPaint().apply {
        style = Paint.Style.FILL
        alpha = 255
    }

    private val paintText = Paint().apply {
        color = colorTintSelected
        isAntiAlias = true
        textSize = context.dip(14).toFloat()
    }

    private val baseHeight = context.dip(56) * 1f + 1f

    private val scaleAmplitude = 0.3

    private var centerX = 0

    private var centerY = (baseHeight / 2).toInt()

    private val animBadgeFloat = AnimatedPropertyF()

    private var badgeAnim: Animator = valueAnim(animBadgeFloat)

    private val titleRect = Rect()

    private var titleBitmap: Bitmap? = null

    private var titleCanvas: Canvas? = null

    private val titleSrc = Rect()

    private val titleDst = Rect()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(layoutParams.width, baseHeight.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        centerX = width / 2

        drawBgCircle(canvas)
        drawIcon(canvas)
        drawTitle(canvas)
        drawBadge(canvas)
    }

    fun animateBadgeChange() {
        animBadgeFloat.from = 0f
        animBadgeFloat.to = 1f
        animBadgeFloat.setup()

        badgeAnim.cancel()
        badgeAnim.start()
    }

    private fun defaultPaint() = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        strokeWidth = 10f
        isAntiAlias = true
    }

    private inline fun drawBgCircle(canvas: Canvas) {
        sb.centerY = centerY * 1f
        sb.radius = iconHalf * 1.6f
        paintOvalBg.alpha = (animFloat.value * 255).toInt()
        canvas.drawRoundRect(iconHalf, sb.topF, width - iconHalf, sb.bottomF, sb.radius, sb.radius, paintOvalBg)
    }

    private inline fun drawIcon(canvas: Canvas) {
        sb.centerX = iconSize * 1.5f
        sb.centerY = centerY * 1f
        sb.radius = (iconHalf * (1 + scaleAmplitude * Math.sin(animFloat.value * Math.PI))).toFloat()

        drawable.bounds = sb.rect
        drawable.setTint(animTint.value)
        drawable.draw(canvas)
    }

    private inline fun drawTitle(canvas: Canvas) {

        if (checked) {

            paintText.getTextBounds(title, 0, title.length, titleRect)
            if (titleBitmap == null) {
                titleBitmap = Bitmap.createBitmap(titleRect.width(), titleRect.height(), Bitmap.Config.ARGB_4444)

                if (titleCanvas == null) {
                    titleCanvas = Canvas(titleBitmap!!)
                }
                titleCanvas!!.setBitmap(titleBitmap!!)
                titleCanvas!!.drawText(title, 0f, titleRect.height() * 1f, paintText)
            }

            titleSrc.right = Math.min((width - 3f * iconSize).toInt(), titleRect.width())
            titleSrc.bottom = titleRect.height()
            titleSrc.top = 0
            titleSrc.left = 0

            titleDst.left = (iconSize * 2.5f).toInt()
            titleDst.top = centerY - titleRect.height() / 2
            titleDst.bottom = centerY + titleRect.height() / 2
            titleDst.right = Math.min(2.5f * iconSize + titleSrc.width(), width - iconHalf).toInt()

            paintTextBitmap.alpha = (animFloat.value * 255).toInt()
            canvas.drawBitmap(titleBitmap!!, titleSrc, titleDst, paintTextBitmap)
        }
    }

    private inline fun drawBadge(canvas: Canvas) {
        badge.draw(canvas, width - iconSize, centerY - iconHalf, animFloat.value, animBadgeFloat.value)
    }

    private fun setSelectedStateAnimations() {
        animFloat.from = 0f
        animFloat.to = 1f

        animTint.from = colorTintUnselected
        animTint.to = colorTintSelected

        if (checked) animProperties.forEach {
            it.reverse()
        }

        if (!animatorSet.isRunning) {
            animProperties.forEach { it.setup() }
        }
    }

    private fun animateSelectedState() {
        animatorSet.cancel()
        animatorSet.apply {
            playTogether(
                valueAnim(animFloat),
                colorAnim(animTint)
            )
        }.start()
    }

    private fun valueAnim(animatedProperty: AnimatedPropertyF): ValueAnimator {
        return ValueAnimator.ofFloat(animatedProperty.from, animatedProperty.to).apply {
            duration = animationDuration
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                animatedProperty.value = it.animatedValue as Float
                this@SpreadTabView.postInvalidate()
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

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}