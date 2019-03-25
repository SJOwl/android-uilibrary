package au.sjowl.lib.ui.views.buttons.fab.commons

import android.content.Context
import android.graphics.Color
import au.sjowl.lib.ui.views.utils.colorCompat
import au.sjowl.lib.uxlibrary.R

class FmStateCollapsed(context: Context) : FmState() {
    override val properties: Map<Int, Any> = mapOf(
        MULT to 0f,
        COLOR_FAB to context.colorCompat(R.color.fab_bg_collapsed),
        COLOR_FAB_ICON to context.colorCompat(R.color.fab_tint_collapsed),
        COLOR_BG to Color.TRANSPARENT
    )
}