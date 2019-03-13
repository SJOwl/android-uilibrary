package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

class TelegramChartView : View {

    var chartData: ChartData = ChartData()
        set(value) {
            field = value
            chartRange.chartData = value
            charts.clear()
            value.columns.values.forEach { charts.add(Chart(it, value.x, layoutHelper, chartRange)) }
        }

    private val layoutHelper = LayoutHelper().apply {
        paddingBottom = context.dip(24)
        paddingTop = context.dip(20)
        textSize = context.sp(10)
        paddingTextBottom = context.dip(6)
    }

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
        axisY.draw(canvas, measuredWidth)
        axisTime.draw(canvas, measuredWidth, measuredHeight - layoutHelper.paddingBottom)
        val activeCharts = charts.filter { it.column.enabled }
        activeCharts.forEach { it.draw(canvas) }
        if (drawPointer) {
            pointer.draw(canvas, measuredHeight)
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
        val columns = chartData.columns.values
        columns.forEach { it.calculateBorders(chartRange.timeIndexStart, chartRange.timeIndexEnd) }
        val chartsMin = columns.filter { it.enabled }.minBy { it.min }?.min ?: 0
        val chartsMax = columns.filter { it.enabled }.maxBy { it.max }?.max ?: 100
        axisY.adjustValuesRange(chartsMin, chartsMax)

        axisY.onWindowChanged(measuredHeight)
        axisTime.onWindowChanged()
        charts.forEach {
            it.onWindowChanged(measuredWidth, measuredHeight)
        }

        super.invalidate()
    }

    fun onTimeIntervalChanged(timeIndexStart: Int, timeIndexEnd: Int) {
        chartRange.timeIndexStart = timeIndexStart
        chartRange.timeIndexEnd = timeIndexEnd

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