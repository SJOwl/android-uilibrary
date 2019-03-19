package au.sjowl.lib.view.charts

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.charts.Themes.DARK
import au.sjowl.lib.view.charts.Themes.LIGHT
import au.sjowl.lib.view.charts.telegram.recycler.ChartItem
import au.sjowl.lib.view.charts.telegram.recycler.ChartItemAdapter
import au.sjowl.lib.view.charts.telegram.recycler.ChartItemHolderListener
import au.sjowl.lib.view.charts.telegram.recycler.MiddleDividerItemDecoration
import org.jetbrains.anko.dip
import org.jetbrains.anko.margin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.textColor
import org.jetbrains.anko.wrapContent

class ChartContainer : LinearLayout {

    var chartData: ChartData = ChartData()
        set(value) {
            field = value

            titleTextView.text = value.title
            overview.initWith(chartData)
            chart.initWith(chartData)
            chartsAdapter.items = value.columns.values.map { ChartItem(it.id, it.name, it.color, it.enabled) }

            requestLayout()
        }

    var theme = LIGHT
        set(value) {
            field = value
            when (value) {
                LIGHT -> {
                }
                DARK -> {
                }
            }
        }

    private var chart = ChartView(context).apply {
        layoutParams = LayoutParams(matchParent, context.dip(300)).apply {
            marginStart = context.dip(16)
            marginEnd = context.dip(16)
        }
    }

    private var overview = ChartOverview(context).apply {
        layoutParams = LayoutParams(matchParent, context.dip(40)).apply {
            marginStart = context.dip(16)
            marginEnd = context.dip(16)
        }
    }

    private val floatValueAnimator = ValueAnimator().apply {
        setFloatValues(1f, 0f)
        duration = 150
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener {
            val v = animatedValue as Float
            overview.onAnimateValues(v)
            chart.onAnimateValues(v)
        }
    }

    private val chartsAdapter = ChartItemAdapter(object : ChartItemHolderListener {
        override fun onChecked(data: ChartItem, checked: Boolean) {
            onAnimate(floatValueAnimator) {
                chartData.columns[data.chartId]!!.enabled = checked
            }
        }
    })

    private val recyclerView = RecyclerView(context).apply {
        layoutManager = LinearLayoutManager(context)
        adapter = chartsAdapter
        overScrollMode = OVER_SCROLL_NEVER
        addItemDecoration(MiddleDividerItemDecoration(context))
    }

    private val titleTextView = TextView(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent).apply {
            margin = context.dip(16)
        }
        textSize = 14f
        textColor = ContextCompat.getColor(context, R.color.telegram_title)
        typeface = Typeface.DEFAULT_BOLD
    }

    fun init(context: Context, attrs: AttributeSet?) {
        orientation = VERTICAL
        addView(titleTextView)
        addView(chart)
        addView(overview)
        addView(recyclerView)
        // todo invalidate instead
        overview.onTimeIntervalChanged = chart::onTimeIntervalChanged
    }

    private fun onAnimate(animator: ValueAnimator, block: () -> Unit) {
        animator.cancel()

        overview.updateStartPoints()
        chart.updateStartPoints()

        block.invoke()

        overview.updateFinishState()
        chart.updateFinishState()

        animator.start()
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }
}

object Themes {
    const val LIGHT = 0
    const val DARK = 1

    fun toggleTheme(theme: Int): Int {
        return (theme + 1) % 2
    }

    fun styleFromTheme(theme: Int): Int {
        return when (theme) {
            LIGHT -> R.style.AppTheme_Light
            DARK -> R.style.AppTheme_Dark
            else -> throw IllegalStateException("No such theme $theme")
        }
    }
}