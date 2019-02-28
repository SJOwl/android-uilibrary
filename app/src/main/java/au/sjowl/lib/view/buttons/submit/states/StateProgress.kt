package au.sjowl.lib.view.buttons.submit.states

import android.graphics.Canvas
import android.graphics.Color
import au.sjowl.lib.view.buttons.submit.DrawHelper

class StateProgress(
    dh: DrawHelper
) : ButtonState(dh) {

    override val properties = mapOf(
        MULT to 0f,
        COLOR_BACKGROUND to dh.mainColor,
        COLOR_FRAME to dh.mainColor,
        COLOR_TEXT to Color.WHITE
    )

    private val view = dh.view

    override fun draw(canvas: Canvas) {
        val sw = dh.paintFrame.strokeWidth
        val p = view.height * 1f - sw
        var w = view.width * dh.animator.getFloat(MULT)
        if (p < w) {
            canvas.drawRoundRect((view.width - w) / 2, sw / 2, (view.width + w) / 2, view.height * 1f - sw / 2, dh.r, dh.r, dh.paintBackground)
        } else {
            w = p
            val left = (view.width - w) / 2
            val top = sw / 2
            val right = (view.width + w) / 2
            val bottom = view.height * 1f - sw / 2
            val angle = view.progress * 360
            dh.paintBackground.alpha = (255 * dh.animator.getFloat(MULT)).toInt()
            canvas.run {
                drawRoundRect(left, top, right, bottom, dh.r, dh.r, dh.paintBackground)
                drawArc(left, top, right, bottom, -90f, angle, false, dh.paintFrame)
            }
        }
    }
}