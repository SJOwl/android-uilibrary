package au.sjowl.lib.ui.telegramchart.names

import android.view.ViewGroup
import au.sjowl.lib.ui.telegramchart.R
import au.sjowl.lib.ui.telegramchart.recycler.BaseRecyclerViewAdapter
import au.sjowl.lib.ui.telegramchart.recycler.BaseViewHolder

class ChartItemAdapter(
    private val onItemClickListener: ChartItemHolderListener
) : BaseRecyclerViewAdapter<ChartItem, BaseViewHolder>() {

    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.rv_item_chart_title

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ChartItemViewHolder(inflate(parent, viewType), onItemClickListener)
    }
}