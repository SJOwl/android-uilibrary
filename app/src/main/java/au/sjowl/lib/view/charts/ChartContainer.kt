package au.sjowl.lib.view.charts

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.matchParent

class ChartContainer : LinearLayout {

    var data: ChartData = ChartData()
        set(value) {
            field = value
            overview.data = value
            chart.chartData = value
            chart.onTimeIntervalChanged(overview.timeStartIndex, overview.timeEndIndex)
        }

    private var overview = TelegramChartOverview(context).apply {
        layoutParams = LayoutParams(matchParent, context.dip(72))
    }

    private var chart = TelegramChartView(context).apply {
        layoutParams = LayoutParams(matchParent, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        chart.layoutParams.height = measuredWidth
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        orientation = VERTICAL
        addView(chart)
        addView(overview)
        overview.onTimeIntervalChanged = chart::onTimeIntervalChanged
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }
}