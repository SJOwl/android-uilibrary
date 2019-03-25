package au.sjowl.lib.ui.views.buttons.submit.states

import android.graphics.Canvas
import android.graphics.Color
import au.sjowl.lib.ui.views.buttons.submit.DrawHelper
import au.sjowl.lib.ui.views.utils.drawTextCentered

class StateError(
    dh: DrawHelper
) : ButtonState(dh) {

    override val properties = mapOf(
        MULT to 1f,
        COLOR_BACKGROUND to dh.colorError,
        COLOR_FRAME to dh.mainColor,
        COLOR_TEXT to Color.WHITE
    )

    private val view = dh.view

    override fun draw(canvas: Canvas) {
        dh.paintBackground.color = properties[COLOR_BACKGROUND] as Int
        dh.paintFrame.color = properties[COLOR_FRAME] as Int
        val strokeWidth = dh.paintFrame.strokeWidth
        val cleanHeight = view.height * 1f - strokeWidth
        val w = Math.max(cleanHeight, view.width * dh.animator.getFloat(MULT))
        dh.paintBackground.alpha = (255 * dh.animator.getFloat(MULT)).toInt()
        dh.paintText.alpha = dh.paintBackground.alpha
        val textWidth = dh.paintText.measureText(view.textError)
        canvas.run {
            drawRoundRect((view.width - w) / 2, strokeWidth / 2, (view.width + w) / 2, view.height * 1f - strokeWidth / 2, dh.r, dh.r, dh.paintBackground)
            if (textWidth < w) {
                drawTextCentered(view.textError, view.width / 2f, view.height / 2f - strokeWidth, dh.paintText, dh.rectText)
            }
        }
    }
}