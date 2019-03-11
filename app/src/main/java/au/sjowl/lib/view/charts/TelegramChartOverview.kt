package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import au.sjowl.lib.view.utils.measureDrawingMs
import org.jetbrains.anko.dip

class TelegramChartOverview : View {

    var data: ChartData = ChartData()
        set(value) {
            field = value
            timeStartIndex = value.x.values.lastIndex / 4
            timeEndIndex = value.x.values.lastIndex
            value.columns.values.forEach {
                it.calculateBorders()
                points[it.id] = arrayListOf()
            }
        }

    var onTimeIntervalChanged: ((timeStartIndex: Int, timeEndIndex: Int) -> Unit)? = null

    var timeStartIndex = 0

    var timeEndIndex = 0

    private var timeStartDownIndex = 0

    private var timeEndDownIndex = 0

    private val points = mutableMapOf<String, ArrayList<PointF>>()

    private val paintLine = Paint().apply {
        isAntiAlias = true
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }

    private val paintWindow = Paint().apply {
        isAntiAlias = true
        strokeWidth = 20f
        style = Paint.Style.STROKE
        color = Color.parseColor("#dbe7f0")
    }

    private val paintWindowBackground = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.parseColor("#C4f5f8f9")
    }

    private val path = Path()

    private val padBottom = context.dip(4)

    private val padTop = context.dip(4)

    private val rectTimeWindow = RectF()

    private val rectBgLeft = RectF()

    private val rectBgRight = RectF()

    private val rectBorderLeft = RectF()

    private val rectBorderRight = RectF()

    private val touchWidth = context.dip(10)

    private var touchMode: Int = TOUCH_NONE

    private var xDown = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateChartPoints()
        with(rectBorderLeft) {
            top = 0f
            bottom = measuredHeight * 1f
        }
        with(rectBorderRight) {
            top = 0f
            bottom = measuredHeight * 1f
        }
        with(rectTimeWindow) {
            top = 0f
            bottom = measuredHeight * 1f
        }
        with(rectBgLeft) {
            top = 0f
            bottom = measuredHeight * 1f
            left = 0f
        }
        with(rectBgRight) {
            top = 0f
            bottom = measuredHeight * 1f
            right = measuredWidth * 1f
        }
    }

    // todo bitmap with charts vs redraw each frame
    override fun onDraw(canvas: Canvas) {
        measureDrawingMs("overview") {
            drawCharts(canvas)
            drawBackground(canvas)
            drawWindow(canvas)
        }
    }

    // todo scale with 2 pointers
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                rectBorderLeft.left = rectTimeWindow.left - touchWidth
                rectBorderLeft.right = rectTimeWindow.left + touchWidth

                rectBorderRight.left = rectTimeWindow.right - touchWidth
                rectBorderRight.right = rectTimeWindow.right + touchWidth

                touchMode = when {
                    rectBorderLeft.contains(event.x, event.y) -> TOUCH_SCALE_LEFT
                    rectBorderRight.contains(event.x, event.y) -> TOUCH_SCALE_RIGHT
                    rectTimeWindow.contains(event.x, event.y) -> TOUCH_DRAG
                    else -> TOUCH_NONE
                }
                xDown = event.x
                timeStartDownIndex = timeStartIndex
                timeEndDownIndex = timeEndIndex
            }
            MotionEvent.ACTION_MOVE -> {
                val delta = xDown - event.x
                val deltaIndex = -canvasToIndexInterval(delta.toInt())
                when (touchMode) {
                    TOUCH_NONE -> {
                    }
                    TOUCH_DRAG -> {
                        val w = timeEndIndex - timeStartIndex
                        val s = timeStartDownIndex + deltaIndex
                        val e = timeEndDownIndex + deltaIndex

                        when {
                            s < 0 -> {
                                timeStartIndex = 0
                                timeEndIndex = timeStartIndex + w
                            }
                            e >= data.x.values.size -> {
                                timeEndIndex = data.x.values.size - 1
                                timeStartIndex = timeEndIndex - w
                            }
                            else -> {
                                timeEndIndex = e
                                timeStartIndex = s
                            }
                        }

                        onTimeIntervalChanged?.invoke(timeStartIndex, timeEndIndex)
                        invalidate()
                    }
                    TOUCH_SCALE_LEFT -> {
                        timeStartIndex = Math.min(timeStartDownIndex + deltaIndex, timeEndIndex)
                        onTimeIntervalChanged?.invoke(timeStartIndex, timeEndIndex)
                        invalidate()
                    }
                    TOUCH_SCALE_RIGHT -> {
                        timeEndIndex = Math.max(timeEndDownIndex + deltaIndex, timeStartIndex)
                        onTimeIntervalChanged?.invoke(timeStartIndex, timeEndIndex)
                        invalidate()
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                touchMode = TOUCH_NONE
            }
        }
        return true
    }

    private fun calculateChartPoints() {
        val xmin = data.x.min
        val xInterval = data.x.interval
        val h = 1f * (measuredHeight - padBottom - padTop)
        val mh = measuredHeight * 1f - padBottom
        val kX = measuredWidth * 1f / xInterval

        data.columns.values.forEach { chart ->

            val chartPoints = points[chart.id]!!
            chartPoints.clear()

            val chartHeight = chart.height
            val chartMin = chart.min
            val kY = h / chartHeight

            for (i in 0 until data.x.values.size) {
                val x = kX * (data.x.values[i] - xmin)
                val y = mh - kY * (chart.values[i] - chartMin)
                chartPoints.add(PointF(x, y))
            }
        }
    }

    private fun drawCharts(canvas: Canvas) {
        data.columns.values.forEach { chart ->
            if (chart.enabled) {
                with(path) {
                    reset()
                    val chartPoints = points[chart.id]!!
                    moveTo(chartPoints[0].x, chartPoints[0].y)
                    for (i in 0 until chartPoints.size) {
                        lineTo(chartPoints[i].x, chartPoints[i].y)
                    }
                }
                paintLine.color = chart.color
                canvas.drawPath(path, paintLine)
            }
        }
    }

    private fun drawWindow(canvas: Canvas) {
        with(rectTimeWindow) {
            left = timeToCanvas(timeStartIndex)
            right = timeToCanvas(timeEndIndex)
        }
        canvas.drawRect(rectTimeWindow, paintWindow)
    }

    private fun drawBackground(canvas: Canvas) {
        rectBgLeft.right = timeToCanvas(timeStartIndex)
        rectBgRight.left = timeToCanvas(timeEndIndex)
        canvas.drawRect(rectBgLeft, paintWindowBackground)
        canvas.drawRect(rectBgRight, paintWindowBackground)
    }

    private inline fun timeToCanvas(timeIndex: Int): Float = measuredWidth * 1f * timeIndex / data.x.values.size

    private inline fun canvasToIndexInterval(canvasDistance: Int): Int = canvasDistance * data.x.values.size / measuredWidth

    companion object {
        private val TOUCH_NONE = -1
        private val TOUCH_DRAG = 0
        private val TOUCH_SCALE_LEFT = 1
        private val TOUCH_SCALE_RIGHT = 2
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}