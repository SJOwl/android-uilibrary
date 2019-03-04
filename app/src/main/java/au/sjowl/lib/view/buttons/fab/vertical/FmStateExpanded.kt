package au.sjowl.lib.view.buttons.fab.vertical

import android.graphics.Color

class FmStateExpanded : FmState() {
    override val properties: Map<Int, Any> = mapOf(
        MULT to 1f,
        COLOR_FAB to Color.BLACK,
        COLOR_FAB_ICON to Color.WHITE
    )
}