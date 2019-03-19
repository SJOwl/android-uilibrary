package au.sjowl.lib.view.charts

import android.content.Context
import android.graphics.Paint
import au.sjowl.lib.uxlibrary.R
import org.jetbrains.anko.sp

class TelegramPaints(context: Context) {

    val colorLines = context.colorCompat(R.color.telegram_lines)

    val colorText = context.colorCompat(R.color.telegram_chart_text)

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

    val paintOverviewLine = Paint().apply {
        isAntiAlias = true
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    val paintOverviewWindowVerticals = Paint().apply {
        isAntiAlias = true
        strokeWidth = 12f
        style = Paint.Style.STROKE
        color = context.colorCompat(R.color.telegram_overview_window)
    }

    val paintOverviewWindowHorizontals = Paint().apply {
        isAntiAlias = true
        strokeWidth = 3f
        style = Paint.Style.STROKE
        color = context.colorCompat(R.color.telegram_overview_window)
    }

    val paintOverviewWindowTint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = context.colorCompat(R.color.telegram_overview_tint)
    }
}