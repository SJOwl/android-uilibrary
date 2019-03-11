package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class TelegramChartView : View {

    var data: ChartData? = null

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.GREEN)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}