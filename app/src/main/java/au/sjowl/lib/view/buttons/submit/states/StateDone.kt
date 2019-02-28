package au.sjowl.lib.view.buttons.submit.states

import android.graphics.Canvas
import android.graphics.Color
import au.sjowl.lib.view.buttons.submit.DrawHelper

class StateDone(
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
        dh.paintBackground.color = properties[COLOR_BACKGROUND] as Int
        dh.paintFrame.color = properties[COLOR_FRAME] as Int
        val sw = dh.paintFrame.strokeWidth
        val p = view.height * 1f - sw
        var w = view.width * dh.animator.getFloat(MULT)
        println("width = $w, mult = ${dh.animator.getFloat(MULT)}")
        w = Math.max(p, w)
        dh.paintBackground.alpha = (255 * dh.animator.getFloat(MULT)).toInt()
        canvas.run {
            drawRoundRect((width - w) / 2, sw / 2, (width + w) / 2, height * 1f - sw / 2, dh.r, dh.r, dh.paintBackground)
            val svgw = dh.doneSvg.intrinsicWidth / 2
            val cx = width / 2
            val cy = height / 2
            val m = (svgw * dh.animator.getFloat(MULT)).toInt()
            dh.doneSvg.run {
                setBounds(cx - m, cy - svgw, cx + m, cy + svgw)
                setTint(Color.WHITE)
                alpha = (255 * dh.animator.getFloat(MULT)).toInt()
                draw(canvas)
            }
        }
    }
}