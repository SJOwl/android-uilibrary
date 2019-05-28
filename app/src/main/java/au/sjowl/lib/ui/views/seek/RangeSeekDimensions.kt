package au.sjowl.lib.ui.views.seek

import android.content.Context
import org.jetbrains.anko.dip

class RangeSeekDimensions(context: Context) {
    val width = context.dip(3) * 1f
    val pointerRadius = context.dip(12) * 1f
    val pointerTouchRadius = context.dip(20) * 1f
    val markerRadius = context.dip(3) * 1f
    val shadowRadius = context.dip(6) * 1f
}