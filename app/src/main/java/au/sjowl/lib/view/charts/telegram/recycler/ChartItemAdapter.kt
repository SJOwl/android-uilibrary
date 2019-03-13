package au.sjowl.lib.view.charts.telegram.recycler

import android.view.ViewGroup
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.recycler.BaseRecyclerViewAdapter
import au.sjowl.lib.view.recycler.BaseViewHolder

class ChartItemAdapter(
    private val onItemClickListener: ChartItemHolderListener
) : BaseRecyclerViewAdapter<ChartItem, BaseViewHolder>() {

    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.rv_item_chart_item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ChartItemViewHolder(inflate(parent, viewType), onItemClickListener)
    }
}