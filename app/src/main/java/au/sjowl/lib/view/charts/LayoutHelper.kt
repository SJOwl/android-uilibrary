package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Paint
import androidx.core.content.ContextCompat
import au.sjowl.lib.uxlibrary.R
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp

class LayoutHelper(context: Context) {

    var w = 0f

    var h = 0f

    val paddingBottom = context.dip(24)

    val paddingTop = context.dip(20)

    val paddingTextBottom = context.dip(6)

    val colorLines = context.colorCompat(R.color.telegram_lines)

    val colorText = context.colorCompat(R.color.telegram_chart_text)

    val pointerCircleRadius = 20f

    val yMarks = 5

    val xMarks = 5

    val paintGrid = Paint().apply {
        isAntiAlias = true
        color = colorLines
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    val paintText = Paint().apply {
        isAntiAlias = true
        color = colorText
        textSize = context.sp(12f) * 1f
    }

    val paintChartLine = Paint().apply {
        isAntiAlias = true
        strokeWidth = 6f
        style = Paint.Style.STROKE
    }

    val paintPointerCircle = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = context.colorCompat(R.color.telegram_pointer)
    }
}

fun Context.colorCompat(colorId: Int) = ContextCompat.getColor(this, colorId)