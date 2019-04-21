package au.sjowl.lib.ui.views.animations

import android.view.View
import android.view.animation.ScaleAnimation
import au.sjowl.lib.ui.views.animations.interpolators.SinusDissipationInterpolator

fun View.runScaleAnimation(scaleFrom: Float = 0.5f, scaleTo: Float = 1.0f, duration: Long = 250L) {
    startAnimation(ScaleAnimation(scaleFrom, scaleTo, scaleFrom, scaleTo, width / 2f, height / 2f).apply {
        interpolator = SinusDissipationInterpolator()
        this.duration = duration
    })
}