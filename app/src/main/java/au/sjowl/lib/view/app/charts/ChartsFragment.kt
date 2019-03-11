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
        chartOverview0.data = data[0]
        chartOverview0.onTimeIntervalChanged = { timeStart, timeEnd ->
            println("$timeStart->$timeEnd")
        }
        chartOverview1.data = data[1]
        chartOverview1.onTimeIntervalChanged = { timeStart, timeEnd ->
            println("$timeStart->$timeEnd")
        }
        chartOverview2.data = data[2]
        chartOverview2.onTimeIntervalChanged = { timeStart, timeEnd ->
            println("$timeStart->$timeEnd")
        }
        chartOverview3.data = data[3]
        chartOverview3.onTimeIntervalChanged = { timeStart, timeEnd ->
            println("$timeStart->$timeEnd")
        }

        chartOverview4.data = data[4]
        chartOverview4.onTimeIntervalChanged = { timeStart, timeEnd ->
            println("$timeStart->$timeEnd")
        }
    }
}