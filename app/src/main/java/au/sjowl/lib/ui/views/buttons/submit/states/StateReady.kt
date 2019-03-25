package au.sjowl.lib.ui.views.buttons.submit.states

import android.graphics.Canvas
import android.graphics.Color
import au.sjowl.lib.ui.views.buttons.submit.DrawHelper
import au.sjowl.lib.ui.views.utils.drawTextCentered

class StateReady(
    dh: DrawHelper
) : ButtonState(dh) {

    override val properties = mapOf(
        MULT to 1f,
        COLOR_BACKGROUND to dh.mainColor,
        COLOR_FRAME to dh.mainColor,
        COLOR_TEXT to Color.WHITE
    )

    private val view = dh.view

    override fun draw(canvas: Canvas) {
        val sw = dh.paintFrame.strokeWidth / 2
        val left = sw
        val top = sw
        val right = view.width * 1f - sw
        val bottom = view.height * 1f - sw
        val centerX = view.width / 2f
        val centerY = view.height / 2f
        canvas.run {
            drawRoundRect(left, top, right, bottom, dh.r, dh.r, dh.paintBackground)
            drawRoundRect(left, top, right, bottom, dh.r, dh.r, dh.paintFrame)
            drawTextCentered(view.text, centerX, centerY, dh.paintText, dh.rectText)
        }
    }
}