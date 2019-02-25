package au.sjowl.lib.view.animations

import android.graphics.drawable.Animatable
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.animations.interpolators.SinusDissipationInterpolator

fun View.runScaleAnimation(scaleFrom: Float = 0.5f, scaleTo: Float = 1.0f, duration: Long = 250L) {
    startAnimation(ScaleAnimation(scaleFrom, scaleTo, scaleFrom, scaleTo, width / 2f, height / 2f).apply {
        interpolator = SinusDissipationInterpolator()
        this.duration = duration
    })
}

fun AnimateImageView(v: ImageView) {
    var b = true
    val cursor = v.context.resources.getDrawable(R.drawable.search_reverse)
    val searchIcon = v.context.resources.getDrawable(R.drawable.search_anim)

    if (b)
        v.setImageDrawable(cursor)
    else
        v.setImageDrawable(searchIcon)
    (v.drawable as Animatable).start()
    b = !b
}