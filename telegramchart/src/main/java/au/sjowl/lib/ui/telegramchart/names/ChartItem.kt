package au.sjowl.lib.ui.telegramchart.names

import androidx.annotation.ColorInt

data class ChartItem(
    val chartId: String,
    val name: String,
    @ColorInt val color: Int,
    val enabled: Boolean
)