package au.sjowl.lib.ui.views.animations.interpolators.providers

import android.view.animation.DecelerateInterpolator

class DefaultInterpolatorProvider : InterpolatorProvider {
    override fun floatInterpolator() = DecelerateInterpolator()
}