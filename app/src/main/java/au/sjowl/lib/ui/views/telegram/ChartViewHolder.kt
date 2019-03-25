package au.sjowl.lib.ui.views.telegram

import android.view.View
import au.sjowl.lib.ui.telegramchart.data.ChartData
import au.sjowl.lib.ui.views.recycler.BaseViewHolder
import kotlinx.android.synthetic.main.rv_item_chart.view.*

class ChartViewHolder(
    view: View
) : BaseViewHolder(view) {

    override fun bind(item: Any) {
        (item as ChartData)
        with(itemView) {
            item.initTimeWindow()
            chartContainer.updateTheme()
            chartContainer.chartData = item
        }
    }
}