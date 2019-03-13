package au.sjowl.lib.view.charts.telegram.recycler

interface ChartItemHolderListener {
    fun onChecked(data: ChartItem, checked: Boolean)
}