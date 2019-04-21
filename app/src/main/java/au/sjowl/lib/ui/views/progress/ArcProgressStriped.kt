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
import au.sjowl.lib.ui.views.utils.drawArc
import org.jetbrains.anko.dip

class ArcProgressStriped : View {

    var speed = 2000L

    var color = getThemeAccentColor()

    var circles = 3

    private val defaultSize = context.dip(48)

    private var size = 0

    private val strokeW = 8f

    private val paintProgress = Paint().apply {
        color = this@ArcProgressStriped.color
        strokeWidth = strokeW
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private var sizeMax = 0f

    private var center = 0f

    private var animatorSet: AnimatorSet? = AnimatorSet()

    private var s = 0f
        set(value) {
            if (field != value) {
                field = value
                invalidate()
            }
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = defaultSize(widthMeasureSpec)
        val h = defaultSize(heightMeasureSpec)
        size = Math.min(w, h)
        setMeasuredDimension(size, size)

        sizeMax = if (size == w) (size - paddingLeft - paddingRight).toFloat() else (size - paddingTop - paddingBottom).toFloat()
        sizeMax -= 2 * strokeW
        center = size / 2f

        startAnimation()
    }

    override fun onDraw(canvas: Canvas) {
        (1..circles).forEach { i ->
            val r = sizeMax / 2 * i / circles
            val percent = (Math.sin((sizeMax * Math.PI - s) / (sizeMax * Math.PI) * Math.PI)).toFloat()
            var alfa = ((sizeMax * Math.PI - s) / (2 * r * Math.PI)).toFloat()
            if (alfa > 1) alfa = 0f
            canvas.drawArc(center, center, r, alfa * 360 - percent * 30 - 45f, 90f + percent * 30, paintProgress)
        }
    }

    private fun startAnimation() {
        animatorSet?.cancel()
        animatorSet = AnimatorSet()

        val s = ValueAnimator.ofFloat(0f, (sizeMax * Math.PI).toFloat()).apply {
            duration = speed
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ValueAnimator.INFINITE
            addUpdateListener {
                s = it.animatedValue as Float
            }
        }
        animatorSet?.apply {
            playTogether(s)
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