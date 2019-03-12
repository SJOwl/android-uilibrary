package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
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
        paddingBottom = context.dip(40)
        paddingTop = context.dip(20)
        textSize = context.sp(16)
        paddingTextBottom = context.dip(4)
    }

    private val chartRange = ChartRange()

    private val charts = arrayListOf<Chart>()

    private val axisY = AxisY(layoutHelper, chartRange)

    private val axisTime = AxisTime(layoutHelper, chartRange)

    private val pointer = Pointer(layoutHelper)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        onTimeIntervalChanged(chartRange.timeIndexStart, chartRange.timeIndexEnd)
    }

    override fun onDraw(canvas: Canvas) {
        axisY.draw(canvas, measuredWidth)
        axisTime.draw(canvas, measuredWidth, measuredHeight)
        charts.forEach { it.draw(canvas) }
        pointer.draw(canvas)
    }

    fun onTimeIntervalChanged(timeIndexStart: Int, timeIndexEnd: Int) {
        chartRange.timeIndexStart = timeIndexStart
        chartRange.timeIndexEnd = timeIndexEnd

        // get valueMin and valueMax for each of charts
        val columns = chartData.columns.values
        columns.forEach { it.calculateBorders(timeIndexStart, timeIndexEnd) }
        val chartsMin = columns.minBy { it.min }!!.min
        val chartsMax = columns.maxBy { it.max }!!.max
        axisY.adjustValuesRange(chartsMin, chartsMax)

        axisY.onWindowChanged(measuredHeight)
        axisTime.onWindowChanged()
        charts.forEach {
            it.onWindowChanged(measuredWidth, measuredHeight)
        }

        invalidate()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}

// charts: List<Chart>
// axisY (+horizontal grid)
// axisTime
// pointer (circles at intersection, vertical line, info window)
