package au.sjowl.lib.view.app.charts

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.charts.ChartColumnJsonParser
import au.sjowl.lib.view.charts.Themes
import kotlinx.android.synthetic.main.fr_charts.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.random.Random

class ChartsFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_charts

    private var theme: Int = Themes.LIGHT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = ResourcesUtils.getResourceAsString("chart_data.json")
        val data = ChartColumnJsonParser(json).parse()
        val chartColumn = data[4].columns["y0"]!!
        for (i in 0 until chartColumn.values.size) {
            chartColumn.values[i] += Random.nextInt(200) + 200
        }
        // todo create rv
        data[4].initTimeWindow()
        chartContainer0.theme = theme
        chartContainer0.chartData = data[4]

        menuTheme.onClick {
            theme = Themes.toggleTheme(theme)
            chartContainer0.theme = theme
            setupTheme()
        }
    }

    private fun setupTheme() {
        activity?.setTheme(Themes.styleFromTheme(theme))
        toolbar.backgroundColor = context!!.getColorFromAttr(R.attr.colorToolbar)
        chartContainer0.backgroundColor = context!!.getColorFromAttr(R.attr.colorBackground)
        root.backgroundColor = context!!.getColorFromAttr(R.attr.colorWindow)
        this@ChartsFragment.view?.invalidate()
    }
}

fun Context.getColorFromAttr(attrId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    return typedValue.data
}