package au.sjowl.lib.view.charts.telegram.recycler

import androidx.annotation.ColorInt

data class ChartItem(
    val chartId: String,
    val name: String,
    @ColorInt val color: Int,
    val enabled: Boolean
)