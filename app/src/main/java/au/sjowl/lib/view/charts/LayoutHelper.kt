package au.sjowl.lib.view.charts

import android.content.Context
import androidx.core.content.ContextCompat
import org.jetbrains.anko.dip

class LayoutHelper(context: Context) {

    var w = 0f

    var h = 0f

    val paddingBottom = context.dip(24)

    val paddingTop = context.dip(20)

    val paddingTextBottom = context.dip(6)

    val pointerCircleRadius = 20f

    val yMarks = 5

    val xMarks = 5

    val paints = TelegramPaints(context)
}

fun Context.colorCompat(colorId: Int) = ContextCompat.getColor(this, colorId)