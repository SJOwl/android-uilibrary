package au.sjowl.lib.view.app.charts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.charts.telegram.ChartAdapter
import au.sjowl.lib.view.charts.telegram.ChartColumnJsonParser
import au.sjowl.lib.view.charts.telegram.Themes
import au.sjowl.lib.view.telegramchart.getColorFromAttr
import kotlinx.android.synthetic.main.fr_charts.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk27.coroutines.onClick

class TelegramChartsFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_charts

    private var theme: Int = Themes.LIGHT

    private val chartsAdapter = ChartAdapter().apply {
        val json = ResourcesUtils.getResourceAsString("chart_data.json")
        val data = ChartColumnJsonParser(json).parse()
        items = data
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chartsRecyclerView.layoutManager = LinearLayoutManager(context)
        chartsRecyclerView.adapter = chartsAdapter

        menuTheme.onClick {
            theme = Themes.toggleTheme(theme)

            activity?.setTheme(Themes.styleFromTheme(theme))

            val pos = (chartsRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            chartsRecyclerView.adapter = chartsAdapter
            chartsRecyclerView.scrollToPosition(pos)

            toolbar.backgroundColor = context!!.getColorFromAttr(R.attr.colorToolbar)
            root.backgroundColor = context!!.getColorFromAttr(R.attr.colorWindow)

            this@TelegramChartsFragment.view?.invalidate()
        }
    }
}