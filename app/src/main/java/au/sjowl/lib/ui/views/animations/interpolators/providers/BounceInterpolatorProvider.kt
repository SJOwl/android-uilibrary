package au.sjowl.lib.ui.views.animations.interpolators.providers

import android.view.animation.BounceInterpolator

class BounceInterpolatorProvider : InterpolatorProvider {
    override fun floatInterpolator() = BounceInterpolator()
}