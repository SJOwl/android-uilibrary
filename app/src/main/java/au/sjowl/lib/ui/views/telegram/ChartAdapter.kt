package au.sjowl.lib.ui.views.telegram

import android.view.ViewGroup
import au.sjowl.lib.ui.telegramchart.data.ChartData
import au.sjowl.lib.ui.views.recycler.BaseRecyclerViewAdapter
import au.sjowl.lib.ui.views.recycler.BaseViewHolder
import au.sjowl.lib.uxlibrary.R

class ChartAdapter : BaseRecyclerViewAdapter<ChartData, BaseViewHolder>() {

    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.rv_item_chart

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ChartViewHolder(inflate(parent, viewType))
    }
}