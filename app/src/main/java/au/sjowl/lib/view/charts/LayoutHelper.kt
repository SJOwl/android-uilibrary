package au.sjowl.lib.view.charts

import android.graphics.Color
import android.graphics.Paint

class LayoutHelper {
    var paddingBottom = 0
    var paddingTop = 0
    var textSize = 0
        set(value) {
            field = value
            paintText.textSize = value.toFloat()
        }
    var paddingTextBottom = 0
    var colorLines = Color.parseColor("#f1f1f2")
    var colorText = Color.parseColor("#96a2aa")
        set(value) {
            field = value
            paintText.color = value
        }

    var pointerCircleRadius = 20f

    var yMarks = 5
    var xMarks = 5

    val paintGrid = Paint().apply {
        isAntiAlias = true
        color = colorLines
        style = Paint.Style.STROKE
        strokeWidth = 3f
    }

    val paintText = Paint().apply {
        isAntiAlias = true
        color = colorText
        textSize = this@LayoutHelper.textSize.toFloat()
    }

    val paintChartLine = Paint().apply {
        isAntiAlias = true
        strokeWidth = 6f
        style = Paint.Style.STROKE
    }

    val paintBackground = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = Color.WHITE // todo
    }
}