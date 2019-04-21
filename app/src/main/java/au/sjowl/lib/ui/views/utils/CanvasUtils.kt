package au.sjowl.lib.ui.views.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF

private val rectF = RectF()

fun Canvas.drawCompatRoundRect(left: Float, top: Float, right: Float, bottom: Float, rx: Float, ry: Float, paint: Paint) {
    if (android.os.Build.VERSION.SDK_INT >= 21) {
        drawRoundRect(left, top, right, bottom, rx, ry, paint)
    } else {
        drawRect(left, top, right, bottom, paint)
    }
}

fun Canvas.drawTextCenteredVertically(text: String, x: Float, y: Float, paint: Paint, r: Rect) {
    paint.getTextBounds(text, 0, text.length, r)
    drawText(text, x, y + r.height() / 2, paint)
}

fun Canvas.drawTextCentered(text: String, x: Float, y: Float, paint: Paint, r: Rect) {
    paint.getTextBounds(text, 0, text.length, r)
    drawText(text, x - r.width() / 2, y + r.height() / 2, paint)
}

fun Canvas.drawOval(centerX: Float, centerY: Float, radius: Float, paint: Paint) {
    with(rectF) {
        left = centerX - radius
        top = centerY - radius
        right = centerX + radius
        bottom = centerY + radius
    }

    drawOval(rectF, paint)
}

fun Canvas.drawArc(centerX: Float, centerY: Float, radius: Float, angleStart: Float, angleSweep: Float, paint: Paint) {
    with(rectF) {
        left = centerX - radius
        top = centerY - radius
        right = centerX + radius
        bottom = centerY + radius
    }
    drawArc(rectF, angleStart, angleSweep, false, paint)
}

fun Canvas.drawCompatArc(left: Float, top: Float, right: Float, bottom: Float, startAngle: Float, sweepAngle: Float, useCenter: Boolean, paint: Paint) {
    rectF.left = left
    rectF.top = top
    rectF.right = right
    rectF.bottom = bottom
    drawArc(rectF, startAngle, sweepAngle, useCenter, paint)
}