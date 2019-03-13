package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.charts.telegram.recycler.ChartItem
import au.sjowl.lib.view.charts.telegram.recycler.ChartItemAdapter
import au.sjowl.lib.view.charts.telegram.recycler.ChartItemHolderListener
import au.sjowl.lib.view.charts.telegram.recycler.MiddleDividerItemDecoration
import org.jetbrains.anko.dip
import org.jetbrains.anko.margin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textColor
import org.jetbrains.anko.wrapContent

class ChartContainer : LinearLayout {

    var chartData: ChartData = ChartData()
        set(value) {
            field = value
            titleTextView.text = value.title
            overview.data = value
            chart.chartData = value
            chart.onTimeIntervalChanged(overview.timeStartIndex, overview.timeEndIndex)
            chartsAdapter.items = value.columns.values.map { ChartItem(it.id, it.name, it.color, it.enabled) }

            requestLayout()
        }

    private var chart = TelegramChartView(context).apply {
        layoutParams = LayoutParams(matchParent, context.dip(300)).apply {
            marginStart = context.dip(16)
            marginEnd = context.dip(16)
        }
    }

    private var overview = TelegramChartOverview(context).apply {
        layoutParams = LayoutParams(matchParent, context.dip(40)).apply {
            marginStart = context.dip(16)
            marginEnd = context.dip(16)
        }
    }

    private val chartsAdapter = ChartItemAdapter(object : ChartItemHolderListener {
        override fun onChecked(data: ChartItem, checked: Boolean) {
            chartData.columns[data.chartId]!!.enabled = checked
            overview.onChartsChanged()
            chart.invalidate()
        }
    })

    private val recyclerView = RecyclerView(context).apply {
        layoutManager = LinearLayoutManager(context)
        adapter = chartsAdapter
        overScrollMode = OVER_SCROLL_NEVER
        addItemDecoration(MiddleDividerItemDecoration(context))
    }

    private val titleTextView = TextView(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent).apply {
            margin = context.dip(16)
        }
        textSize = 14f
        textColor = ContextCompat.getColor(context, R.color.telegram_title)
        typeface = Typeface.DEFAULT_BOLD
    }

    fun init(context: Context, attrs: AttributeSet?) {
        orientation = VERTICAL
        addView(titleTextView)
        addView(chart)
        addView(overview)
        addView(recyclerView)
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