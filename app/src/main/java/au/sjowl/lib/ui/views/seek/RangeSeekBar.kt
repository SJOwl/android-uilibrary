package au.sjowl.lib.ui.views.seek

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.os.Vibrator
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import au.sjowl.lib.ui.views.utils.AnimatedPropertyF

class RangeSeekBar : View {

    var totalStars = 5

    var min
        get() = pointerStart.position
        private set(value) {
            pointerStart.position = value
        }

    var max
        get() = pointerEnd.position
        private set(value) {
            pointerEnd.position = value
        }

    var animationDuration = 150L

    private val dimens = RangeSeekDimensions(context)

    private val paints = RangeSeekPaints(context, dimens)

    private val pointerStart = SeekPointer(context)

    private val pointerEnd = SeekPointer(context)

    private var onRangeChangedListener: ((start: Int, end: Int) -> Unit) = { start, end -> Unit }

    private val step get() = (measuredWidth - paddingLeft - paddingRight - 2 * dimens.pointerTouchRadius) / (totalStars - 1)

    private val start get() = paddingLeft + dimens.pointerTouchRadius

    private val end get() = (measuredWidth - paddingRight - dimens.pointerTouchRadius).toFloat()

    private val animatedPropertyF = AnimatedPropertyF(0f, 0f, 1f)

    private val valueAnimator = valueAnim(animatedPropertyF)

    private var xPrevious = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h = defaultSize(heightMeasureSpec, (dimens.pointerTouchRadius * 2).toInt() + paddingTop + paddingBottom)
        val w = defaultSize(widthMeasureSpec, (dimens.pointerTouchRadius * 2).toInt() + paddingRight + paddingLeft)
        setMeasuredDimension(w, h)

        pointerStart.measure(measuredWidth * 1f, measuredHeight * 1f, start, step)
        pointerEnd.measure(measuredWidth * 1f, measuredHeight * 1f, start, step)
    }

    override fun onDraw(canvas: Canvas) {
        val start = this.start
        val y = measuredHeight / 2f
        val step = this.step

        canvas.drawLine(start, y, end, y, paints.lineUnselected)
        canvas.drawLine(pointerStart.currentX, y, pointerEnd.currentX, y, paints.lineSelected)

        var x = start
        var i = 1
        while (x < pointerStart.currentX && i <= pointerStart.position) {
            x = start + step * (i - 1)
            canvas.drawCircle(x, y, dimens.markerRadius, paints.lineUnselected)
            i++
        }
        while (x < pointerEnd.currentX && i <= pointerEnd.position) {
            x = start + step * (i - 1)
            canvas.drawCircle(x, y, dimens.markerRadius, paints.lineSelected)
            i++
        }
        while (x < measuredWidth && i <= totalStars) {
            x = start + step * (i - 1)
            canvas.drawCircle(x, y, dimens.markerRadius, paints.lineUnselected)
            i++
        }

        pointerStart.draw(canvas, animatedPropertyF.value)
        pointerEnd.draw(canvas, animatedPropertyF.value)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                animatedPropertyF.value = 0f
                pointerStart.startDrag(event.x)
                pointerEnd.startDrag(event.x)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(xPrevious - event.x) > 1) {
                    xPrevious = event.x
                    val t = step * 0.05f
                    if (
                        event.x in start..end &&
                        ((pointerStart.isDragging && event.x < pointerEnd.currentX - step - t) ||
                            (pointerEnd.isDragging && event.x > pointerStart.currentX + step + t))
                    ) {

                        pointerEnd.move(event.x)
                        pointerStart.move(event.x)
                    }
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                pointerStart.stopDrag()
                pointerEnd.stopDrag()
                valueAnimator.start()
            }
        }
        return true
    }

    fun selectRange(min: Int, max: Int) {
        this.min = Math.max(1, min)
        this.max = Math.min(max, totalStars)
        invalidate()
    }

    fun onRangeChanged(listener: ((start: Int, end: Int) -> Unit)) {
        onRangeChangedListener = listener
    }

    private fun valueAnim(animatedProperty: AnimatedPropertyF): ValueAnimator {
        return ValueAnimator.ofFloat(animatedProperty.from, animatedProperty.to).apply {
            duration = animationDuration
            interpolator = OvershootInterpolator()
            addUpdateListener {
                animatedProperty.value = it.animatedValue as Float
                this@RangeSeekBar.invalidate()
            }
        }
    }

    private fun defaultSize(measureSpec: Int, size: Int): Int {
        var result = size
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        when (specMode) {
            MeasureSpec.AT_MOST -> result = size
            MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY -> result = specSize
        }
        return result
    }

    private fun onPointerMoved() {
        onRangeChangedListener(pointerStart.position, pointerEnd.position)
        vibrate(20)
    }

    init {
        selectRange(min, max)
        pointerStart.onPositionChangedListener = ::onPointerMoved
        pointerEnd.onPositionChangedListener = ::onPointerMoved
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}

fun View.vibrate(ms: Int) {
    (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).vibrate(ms.toLong())
}