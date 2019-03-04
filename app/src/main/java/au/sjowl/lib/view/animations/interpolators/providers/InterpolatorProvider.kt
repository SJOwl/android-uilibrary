package au.sjowl.lib.view.animations.interpolators.providers

import android.animation.TimeInterpolator
import android.view.animation.DecelerateInterpolator

interface InterpolatorProvider {
    fun floatInterpolator(): TimeInterpolator = DecelerateInterpolator()
}