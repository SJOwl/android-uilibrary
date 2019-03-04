package au.sjowl.lib.view.buttons.fab.vertical

import android.graphics.Color

class FmStateCollapsed : FmState() {
    override val properties: Map<Int, Any> = mapOf(
        MULT to 0f,
        COLOR_FAB to Color.WHITE,
        COLOR_FAB_ICON to Color.BLACK
    )
}