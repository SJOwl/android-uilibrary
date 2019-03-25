package au.sjowl.lib.ui.views.animations.interpolators.providers

import android.animation.TimeInterpolator
import android.view.animation.DecelerateInterpolator

interface InterpolatorProvider {
    fun floatInterpolator(): TimeInterpolator = DecelerateInterpolator()
}