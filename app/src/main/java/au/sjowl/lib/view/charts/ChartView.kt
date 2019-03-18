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
            chartRange.chartData = value
            charts.clear()
            value.columns.values.forEach { charts.add(Chart(it, value.x, layoutHelper, chartRange)) }
        }

    private val layoutHelper = LayoutHelper(context)

    private val chartRange = ChartRange()

    private val charts = arrayListOf<Chart>()

    private val axisY = AxisY(layoutHelper, chartRange)

    private val axisTime = AxisTime(layoutHelper, chartRange)

    private val pointer = Pointer(layoutHelper, chartRange)

    private var drawPointer = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        axisY.draw(canvas)
        axisTime.draw(canvas)
        val activeCharts = charts.filter { it.column.enabled }
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

    // todo animate changes for each of charts
    fun onChartsChanged() {
        invalidate()
    }

    override fun invalidate() {
        layoutHelper.w = measuredWidth * 1f
        layoutHelper.h = measuredHeight * 1f

        val columns = chartData.columns.values
        columns.forEach { it.calculateBorders(chartRange.timeIndexStart, chartRange.timeIndexEnd) }
        val chartsMin = columns.filter { it.enabled }.minBy { it.min }?.min ?: 0
        val chartsMax = columns.filter { it.enabled }.maxBy { it.max }?.max ?: 100
        axisY.adjustValuesRange(chartsMin, chartsMax)

        axisY.onWindowChanged()
        axisTime.onWindowChanged()
        charts.forEach {
            it.onWindowChanged()
        }

        super.invalidate()
    }

    fun initWith(chartData: ChartData) {
        this.chartData = chartData
        chartRange.timeIndexStart = chartData.timeIndexStart
        chartRange.timeIndexEnd = chartData.timeIndexEnd
        chartRange.scaleInProgress = false

        invalidate()
    }

    fun onTimeIntervalChanged(timeIndexStart: Int, timeIndexEnd: Int, inProgress: Boolean) {
        chartRange.timeIndexStart = timeIndexStart
        chartRange.timeIndexEnd = timeIndexEnd
        chartRange.scaleInProgress = inProgress

        invalidate()
    }

    private inline fun updateTimeIndexFromX(x: Float) {
        if (charts.size > 0) {
            chartRange.pointerTimeX = charts[0].getPointerX()
            chartRange.pointerTimeIndex = (chartRange.timeIntervalIndexes * x / measuredWidth).toInt()
            invalidate()
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}