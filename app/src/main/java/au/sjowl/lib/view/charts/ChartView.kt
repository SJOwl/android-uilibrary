package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ChartView : View {

    private var chartData: ChartData = ChartData()
        set(value) {
            field = value
            charts.clear()
            axisY.chartData = value
            axisTime.chartData = value
            value.columns.values.forEach { charts.add(Chart(it, layoutHelper, value)) }
        }

    private val layoutHelper = LayoutHelper(context)

    private val charts = arrayListOf<Chart>()

    private val axisY = AxisY(layoutHelper, chartData)

    private val axisTime = AxisTime(layoutHelper, chartData)

    private val pointer = Pointer(layoutHelper, chartData)

    private var drawPointer = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        axisY.draw(canvas)
        axisTime.draw(canvas)
        val activeCharts = charts
        activeCharts.forEach { it.draw(canvas) }
        if (drawPointer) {
            pointer.draw(canvas)
            activeCharts.forEach { it.drawPointer(canvas) }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPointer = true
                updateTimeIndexFromX(event.x)
            }
            MotionEvent.ACTION_MOVE -> {
                updateTimeIndexFromX(event.x)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                drawPointer = false
                invalidate()
            }
        }
        return true
    }

    override fun invalidate() {
        updateFinishState()
        super.invalidate()
    }

    fun updateFinishState() {
        layoutHelper.w = measuredWidth * 1f
        layoutHelper.h = measuredHeight * 1f

        adjustValueRange()

        axisY.onAnimateValues(0f)
        axisTime.onWindowChanged()
        charts.forEach { it.onWindowChanged() }
    }

    fun updateStartPoints() {
        axisY.updateStartPoints()
        charts.forEach { it.updateStartPoints() }
    }

    fun onAnimateValues(v: Float) {
        axisY.onAnimateValues(v)
        charts.forEach { it.onAnimateValues(v) }
        super.invalidate()
    }

    fun initWith(chartData: ChartData) {
        this.chartData = chartData
        chartData.scaleInProgress = false

        invalidate()
    }

    fun onTimeIntervalChanged(timeIndexStart: Int, timeIndexEnd: Int, inProgress: Boolean) {
        chartData.timeIndexStart = timeIndexStart
        chartData.timeIndexEnd = timeIndexEnd
        chartData.scaleInProgress = inProgress

        invalidate()
    }

    private fun adjustValueRange() {
        val columns = chartData.columns.values
        columns.forEach { it.calculateBorders(chartData.timeIndexStart, chartData.timeIndexEnd) }
        val enabled = columns.filter { it.enabled }
        val chartsMin = enabled.minBy { it.min }?.min ?: 0
        val chartsMax = enabled.maxBy { it.max }?.max ?: 100
        axisY.adjustValuesRange(chartsMin, chartsMax)
    }

    private inline fun updateTimeIndexFromX(x: Float) {
        if (charts.size > 0) {
            chartData.pointerTimeX = charts[0].getPointerX()
            chartData.pointerTimeIndex = (chartData.timeIntervalIndexes * x / measuredWidth).toInt()
            invalidate()
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}