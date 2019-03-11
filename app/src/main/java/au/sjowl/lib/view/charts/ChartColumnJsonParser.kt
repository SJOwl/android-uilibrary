package au.sjowl.lib.view.charts

import android.graphics.Color
import org.json.JSONArray
import org.json.JSONObject

class ChartColumnJsonParser(val json: String) {

    fun parse(): List<ChartData> {

        val charts = JSONArray(json)
        val chartDataList = arrayListOf<ChartData>()

        for (i in 0 until charts.length()) {
            val chartData = ChartData()

            chartDataList.add(chartData)

            val chart = charts[i] as JSONObject

            val colors = chart.getJSONObject("colors")
            colors.keys().forEach { key ->
                chartData.columns[key] = ChartColumn(key).apply {
                    color = Color.parseColor(colors.getString(key))
                }
            }

            val names = chart.getJSONObject("names")
            names.keys().forEach { key ->
                chartData.columns[key]?.name = names.getString(key)
            }

            val types = chart.getJSONObject("types")
            types.keys().forEach { key ->
                chartData.columns[key]?.type = types.getString(key)
            }

            val columns = chart.getJSONArray("columns")
            for (j in 0 until columns.length()) {
                val jsonColumn = columns[j] as JSONArray
                val key = jsonColumn[0] as String

                if (key == "x") {
                    chartData.x.values
                    for (k in 1 until jsonColumn.length()) {
                        chartData.x.values.add(jsonColumn[k] as Long)
                    }
                } else {
                    val column = chartData.columns[key] as ChartColumn
                    for (k in 1 until jsonColumn.length()) {
                        column.values.add(jsonColumn[k] as Int)
                    }
                }
            }
        }

        return chartDataList
    }
}