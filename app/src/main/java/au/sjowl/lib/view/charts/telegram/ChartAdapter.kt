package au.sjowl.lib.view.charts.telegram

import android.view.ViewGroup
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.recycler.BaseRecyclerViewAdapter
import au.sjowl.lib.view.recycler.BaseViewHolder
import au.sjowl.lib.view.telegramchart.data.ChartData

class ChartAdapter : BaseRecyclerViewAdapter<ChartData, BaseViewHolder>() {

    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.rv_item_chart

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ChartViewHolder(inflate(parent, viewType))
    }
}