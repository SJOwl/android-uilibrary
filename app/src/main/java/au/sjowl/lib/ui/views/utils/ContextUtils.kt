package au.sjowl.lib.ui.views.utils

import android.content.Context
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.transition.Transition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.dip
import org.jetbrains.anko.padding

/* ********************       Context      *********************/
fun Context.getCompatDrawable(drawableId: Int): Drawable {
    return if (Build.VERSION.SDK_INT >= 21) {
        resources.getDrawable(drawableId, null) as Drawable
    } else {
        resources.getDrawable(drawableId) as Drawable
    }
}

/* ********************       View      *********************/
fun View.setElevation(dips: Int) {
    if (Build.VERSION.SDK_INT >= 21) {
        elevation = context.dip(dips).toFloat()
    }
}

fun View.setPaddingDips(dips: Int) {
    padding = context.dip(dips)
}

fun View.tint(color: Int) {
    setLayerType(View.LAYER_TYPE_HARDWARE, Paint().apply {
        colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    })
}

fun Drawable.setTintCompat(color: Int) {
    setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}

fun beginDelayedTransition(parent: ViewGroup, transition: Transition?) {
    if (Build.VERSION.SDK_INT >= 19) {
        if (transition != null)
            TransitionManager.beginDelayedTransition(parent, transition)
        else
            TransitionManager.beginDelayedTransition(parent)
    }
}