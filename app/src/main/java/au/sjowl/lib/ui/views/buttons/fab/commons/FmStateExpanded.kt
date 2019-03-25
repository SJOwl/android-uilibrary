package au.sjowl.lib.ui.views.buttons.fab.commons

import android.content.Context
import au.sjowl.lib.ui.views.utils.colorCompat
import au.sjowl.lib.uxlibrary.R

class FmStateExpanded(context: Context) : FmState() {
    override val properties: Map<Int, Any> = mapOf(
        MULT to 1f,
        COLOR_FAB to context.colorCompat(R.color.fab_bg_expanded),
        COLOR_FAB_ICON to context.colorCompat(R.color.fab_tint_expanded),
        COLOR_BG to context.colorCompat(R.color.fab_bg)
    )
}