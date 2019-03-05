package au.sjowl.lib.view.buttons.fab.commons

import android.content.Context
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.utils.colorCompat

class FmStateExpanded(context: Context) : FmState() {
    override val properties: Map<Int, Any> = mapOf(
        MULT to 1f,
        COLOR_FAB to context.colorCompat(R.color.fab_bg_expanded),
        COLOR_FAB_ICON to context.colorCompat(R.color.fab_tint_expanded),
        COLOR_BG to context.colorCompat(R.color.fab_bg)
    )
}