package au.sjowl.lib.view.app.charts

import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.charts.ChartColumnJsonParser
import kotlinx.android.synthetic.main.fr_charts.*

class ChartsFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_charts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = ResourcesUtils.getResourceAsString("chart_data.json")
        val data = ChartColumnJsonParser(json).parse()
        // todo create rv
        chartContainer0.data = data[0]
//        chartContainer1.chartData = chartData[1]
//        chartContainer2.chartData = chartData[2]
//        chartContainer3.chartData = chartData[3]
//        chartContainer4.chartData = chartData[4]
    }
}