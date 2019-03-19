package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.jetbrains.anko.dip

class OverviewRectangles {

    val rectTimeWindow = RectF()

    val rectBgLeft = RectF()

    val rectBgRight = RectF()

    val rectBorderLeft = RectF()

    val rectBorderRight = RectF()

    fun setVerticalsToAll(top: Float, bottom: Float) {
        rectTimeWindow.top = top
        rectBgLeft.top = top
        rectBgRight.top = top
        rectBorderLeft.top = top
        rectBorderRight.top = top

        rectTimeWindow.bottom = bottom
        rectBgLeft.bottom = bottom
        rectBgRight.bottom = bottom
        rectBorderLeft.bottom = bottom
        rectBorderRight.bottom = bottom
    }

    fun updateTouch(touchWidth: Int) {
        rectBorderLeft.left = rectTimeWindow.left - touchWidth
        rectBorderLeft.right = rectTimeWindow.left + touchWidth

        rectBorderRight.left = rectTimeWindow.right - touchWidth
        rectBorderRight.right = rectTimeWindow.right + touchWidth
    }

    fun getTouchMode(x: Float, y: Float): Int {
        return when {
            rectBorderLeft.contains(x, y) -> ChartOverview.TOUCH_SCALE_LEFT
            rectBorderRight.contains(x, y) -> ChartOverview.TOUCH_SCALE_RIGHT
            rectTimeWindow.contains(x, y) -> ChartOverview.TOUCH_DRAG
            else -> ChartOverview.TOUCH_NONE
        }
    }
}

class ChartOverview : View {

    var chartData: ChartData = ChartData()
        set(value) {
            field = value
            value.columns.values.forEach {
                it.calculateBorders()
                points[it.id] = arrayListOf()
            }
        }

    var onTimeIntervalChanged: ((timeIndexStart: Int, timeIndexEnd: Int, inProgress: Boolean) -> Unit)? = null

    private val rectangles = OverviewRectangles()

    private var timeStartDownIndex = 0

    private var timeEndDownIndex = 0

    private val points = mutableMapOf<String, ArrayList<PointF>>()

    private val path = Path()

    private val padBottom = context.dip(4)

    private val padTop = context.dip(4)

    private val touchWidth = context.dip(10)

    private var touchMode: Int = TOUCH_NONE

    private var xDown = 0f

    private val paints = TelegramPaints(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateChartPoints()
        rectangles.setVerticalsToAll(0f, measuredHeight * 1f)
        rectangles.rectBgLeft.left = 0f
        rectangles.rectBgRight.right = measuredWidth * 1f
    }

    // todo bitmap with charts vs redraw each frame
    override fun onDraw(canvas: Canvas) {
        drawCharts(canvas)
        drawBackground(canvas)
        drawWindow(canvas)
    }

    // todo scale with 2 pointers
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                rectangles.updateTouch(touchWidth)
                touchMode = rectangles.getTouchMode(event.x, event.y)

                xDown = event.x
                timeStartDownIndex = chartData.timeIndexStart
                timeEndDownIndex = chartData.timeIndexEnd
            }
            MotionEvent.ACTION_MOVE -> {
                val delta = xDown - event.x
                val deltaIndex = -canvasToIndexInterval(delta.toInt())
                when (touchMode) {
                    TOUCH_NONE -> {
                    }
                    TOUCH_DRAG -> {
                        val w = chartData.timeIndexEnd - chartData.timeIndexStart
                        val s = timeStartDownIndex + deltaIndex
                        val e = timeEndDownIndex + deltaIndex

                        when {
                            s < 0 -> {
                                chartData.timeIndexStart = 0
                                chartData.timeIndexEnd = chartData.timeIndexStart + w
                            }
                            e >= chartData.x.values.size -> {
                                chartData.timeIndexEnd = chartData.x.values.size - 1
                                chartData.timeIndexStart = chartData.timeIndexEnd - w
                            }
                            else -> {
                                chartData.timeIndexEnd = e
                                chartData.timeIndexStart = s
                            }
                        }
                    }
                    TOUCH_SCALE_LEFT -> {
                        chartData.timeIndexStart = Math.min(timeStartDownIndex + deltaIndex, chartData.timeIndexEnd - SCALE_THRESHOLD)
                        chartData.timeIndexStart = if (chartData.timeIndexStart < 0) 0 else chartData.timeIndexStart
                    }
                    TOUCH_SCALE_RIGHT -> {
                        chartData.timeIndexEnd = Math.max(timeEndDownIndex + deltaIndex, chartData.timeIndexStart + SCALE_THRESHOLD)
                        chartData.timeIndexEnd = if (chartData.timeIndexEnd >= chartData.x.values.size) chartData.x.values.size - 1 else chartData.timeIndexEnd
                    }
                }
                onTimeIntervalChanged?.invoke(chartData.timeIndexStart, chartData.timeIndexEnd, true)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchMode = TOUCH_NONE
                onTimeIntervalChanged?.invoke(chartData.timeIndexStart, chartData.timeIndexEnd, false)
            }
        }
        return true
    }

    fun initWith(chartData: ChartData) {
        this.chartData = chartData
    }

    fun updateStartPoints() {
    }

    fun onAnimateValues(v: Float) {
        invalidate()
    }

    fun updateFinishState() {
    }

    private fun calculateChartPoints() {
        val xmin = chartData.x.min
        val h = 1f * (measuredHeight - padBottom - padTop)
        val mh = measuredHeight * 1f - padBottom
        val kX = measuredWidth * 1f / chartData.x.interval

        val chartsMin = chartData.valueMin
        val chartsMax = chartData.valueMax

        chartData.columns.values.forEach { chart ->

            val chartPoints = points[chart.id]!!
            chartPoints.clear()

            val kY = h / (chartsMax - chartsMin)

            for (i in 0 until chartData.x.values.size) {
                val x = kX * (chartData.x.values[i] - xmin)
                val y = mh - kY * (chart.values[i] - chartsMin)
                chartPoints.add(PointF(x, y))
            }
        }
    }

    private fun drawCharts(canvas: Canvas) {
        chartData.columns.values.forEach { chart ->
            if (chart.enabled) {
                with(path) {
                    reset()
                    val chartPoints = points[chart.id]!!
                    moveTo(chartPoints[0].x, chartPoints[0].y)
                    for (i in 0 until chartPoints.size) {
                        lineTo(chartPoints[i].x, chartPoints[i].y)
                    }
                }
                paints.paintOverviewLine.color = chart.color
                canvas.drawPath(path, paints.paintOverviewLine)
            }
        }
    }

    private fun drawWindow(canvas: Canvas) {
        with(rectangles.rectTimeWindow) {
            left = timeToCanvas(chartData.timeIndexStart)
            right = timeToCanvas(chartData.timeIndexEnd)
        }
        with(canvas) {
            drawLine(rectangles.rectTimeWindow.left, 0f, rectangles.rectTimeWindow.left, measuredHeight * 1f, paints.paintOverviewWindowVerticals)
            drawLine(rectangles.rectTimeWindow.right, 0f, rectangles.rectTimeWindow.right, measuredHeight * 1f, paints.paintOverviewWindowVerticals)
            drawLine(rectangles.rectTimeWindow.left, 0f, rectangles.rectTimeWindow.right, 0f, paints.paintOverviewWindowHorizontals)
            drawLine(rectangles.rectTimeWindow.left, measuredHeight * 1f, rectangles.rectTimeWindow.right, measuredHeight * 1f, paints.paintOverviewWindowHorizontals)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        rectangles.rectBgLeft.right = timeToCanvas(chartData.timeIndexStart)
        rectangles.rectBgRight.left = timeToCanvas(chartData.timeIndexEnd)
        canvas.drawRect(rectangles.rectBgLeft, paints.paintOverviewWindowTint)
        canvas.drawRect(rectangles.rectBgRight, paints.paintOverviewWindowTint)
    }

    private inline fun timeToCanvas(timeIndex: Int): Float = measuredWidth * 1f * timeIndex / chartData.x.values.size

    private inline fun canvasToIndexInterval(canvasDistance: Int): Int = canvasDistance * chartData.x.values.size / measuredWidth

    companion object {
        const val TOUCH_NONE = -1
        const val TOUCH_DRAG = 0
        const val TOUCH_SCALE_LEFT = 1
        const val TOUCH_SCALE_RIGHT = 2
        const val SCALE_THRESHOLD = 10
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}