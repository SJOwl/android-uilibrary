package au.sjowl.lib.ui.views.progress

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import org.jetbrains.anko.dip

class EllipticProgress : View {

    var rectangles = 6

    var speed = 2000L

    var color = getThemeAccentColor()

    private val angle = 360f / rectangles

    private val defaultSize = context.dip(48)

    private var size = 0

    private val strokeW = 3f

    private val paint = Paint().apply {
        color = this@EllipticProgress.color
        strokeWidth = strokeW
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private var sizeMin = 0f

    private var sizeMax = 0f

    private var center = 0f

    private var currentSizeMax = 0f
        set(value) {
            if (field != value) {
                field = value
                postInvalidateOnAnimation()
            }
        }

    private var currentAngle = 0f

    private var animatorSet: AnimatorSet? = AnimatorSet()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = defaultSize(widthMeasureSpec)
        val h = defaultSize(heightMeasureSpec)
        size = Math.min(w, h)
        setMeasuredDimension(size, size)

        sizeMax = if (size == w) (size - paddingLeft - paddingRight).toFloat() else (size - paddingTop - paddingBottom).toFloat()
        sizeMax -= 2 * strokeW
        sizeMin = sizeMax / 2
        center = size / 2f

        startAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        drawRoundRect(canvas, center, center, currentSizeMax / 2, sizeMin / 2, sizeMin, paint)
    }

    fun drawRoundRect(canvas: Canvas, xCenter: Float, yCenter: Float, radiusW: Float, radiusH: Float, round: Float, paint: Paint) {
        repeat(rectangles) {
            canvas.rotate(angle + currentAngle, xCenter, yCenter)
            canvas.drawRoundRect(xCenter - radiusW, yCenter - radiusH, xCenter + radiusW, yCenter + radiusH, round, round, paint)
        }
    }

    private fun startAnimation() {
        animatorSet?.cancel()
        animatorSet = AnimatorSet()
        val size = ValueAnimator.ofFloat(sizeMin, sizeMax).apply {
            duration = speed
            interpolator = AccelerateDecelerateInterpolator()
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                currentSizeMax = it.animatedValue as Float
            }
        }
        val angle = ValueAnimator.ofFloat(0f, 360f).apply {
            duration = speed
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                currentAngle = it.animatedValue as Float
            }
        }
        animatorSet?.apply {
            playTogether(size, angle)
            start()
        }
    }

    private fun defaultSize(measureSpec: Int, size: Int = defaultSize): Int {
        var result = size
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        when (specMode) {
            MeasureSpec.AT_MOST -> result = size
            MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    private fun getThemeAccentColor(): Int {
        val colorAttr: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent
        } else {
            colorAttr = context.resources.getIdentifier("colorAccent", "attr", context.packageName)
        }
        val outValue = TypedValue()
        context.theme.resolveAttribute(colorAttr, outValue, true)
        return outValue.data
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}