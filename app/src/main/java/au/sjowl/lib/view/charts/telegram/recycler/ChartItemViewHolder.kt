package au.sjowl.lib.view.charts.telegram.recycler

import android.view.View
import au.sjowl.lib.view.recycler.BaseViewHolder
import kotlinx.android.synthetic.main.rv_item_chart_item.view.*

class ChartItemViewHolder(
    view: View,
    private val listener: ChartItemHolderListener
) : BaseViewHolder(view) {

    override fun bind(item: Any) {
        (item as ChartItem)
        with(itemView) {
            setOnClickListener {
                checkbox.check()
                listener.onChecked(item, checkbox.checked)
            }
            checkbox.onCheckedChangedListener { checked ->
                listener.onChecked(item, checked)
            }
            title.text = item.name
            checkbox.color = item.color
            checkbox.checked = item.enabled
        }
    }
}