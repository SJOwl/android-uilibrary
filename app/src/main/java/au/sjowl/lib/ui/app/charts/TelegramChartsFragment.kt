package au.sjowl.lib.ui.app.charts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import au.sjowl.lib.ui.app.BaseFragment
import au.sjowl.lib.ui.telegramchart.getColorFromAttr
import au.sjowl.lib.ui.views.telegram.ChartAdapter
import au.sjowl.lib.ui.views.telegram.ChartColumnJsonParser
import au.sjowl.lib.ui.views.telegram.Themes
import au.sjowl.lib.uxlibrary.R
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