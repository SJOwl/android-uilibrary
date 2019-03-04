package au.sjowl.lib.view.animations.interpolators.providers

import android.view.animation.DecelerateInterpolator

class DefaultInterpolatorProvider : InterpolatorProvider {
    override fun floatInterpolator() = DecelerateInterpolator()
}