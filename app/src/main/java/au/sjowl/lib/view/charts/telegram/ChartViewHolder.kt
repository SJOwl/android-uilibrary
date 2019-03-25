package au.sjowl.lib.view.charts.telegram

import android.view.View
import au.sjowl.lib.view.recycler.BaseViewHolder
import au.sjowl.lib.view.telegramchart.data.ChartData
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