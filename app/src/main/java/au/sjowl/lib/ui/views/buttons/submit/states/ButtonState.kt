package au.sjowl.lib.ui.views.buttons.submit.states

import android.content.Context
import android.graphics.Canvas
import au.sjowl.lib.ui.views.buttons.submit.DrawHelper

abstract class ButtonState(
    val dh: DrawHelper
) {

    abstract val properties: Map<Int, Any>

    var onClickListener: (() -> Unit)? = null

    val context: Context = dh.context

    abstract fun draw(canvas: Canvas)

    companion object {
        val COLOR_BACKGROUND = 1
        val COLOR_FRAME = 2
        val COLOR_TEXT = 3
        val MULT = 4
    }
}