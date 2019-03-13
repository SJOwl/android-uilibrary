package au.sjowl.lib.view.charts

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.recycler.BaseRecyclerViewAdapter
import au.sjowl.lib.view.recycler.BaseViewHolder
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

    private val chartsAdapter = ChartItemAdapter(object : ChartItemHolderListener {
        override fun onClick(data: ChartItem) {
            println("click on ${data.name}")
        }
    })

    private var recyclerView = RecyclerView(context).apply {
        layoutManager = LinearLayoutManager(context)
//        adapter = chartsAdapter
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

data class ChartItem(
    val name: String,
    @ColorInt val color: Int
)

interface ChartItemHolderListener {
    fun onClick(data: ChartItem)
}

class ChartItemAdapter(
    private val onItemClickListener: ChartItemHolderListener
) : BaseRecyclerViewAdapter<ChartItem, BaseViewHolder>() {

    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.rv_item_chart_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ChartItemViewHolder(inflate(parent, viewType), onItemClickListener)
    }
}

class ChartItemViewHolder(
    view: View,
    private val listener: ChartItemHolderListener
) : BaseViewHolder(view) {

    override fun bind(item: Any) {
        (item as ChartItem)
        with(itemView) {
            setOnClickListener { listener.onClick(item) }
        }
    }
}