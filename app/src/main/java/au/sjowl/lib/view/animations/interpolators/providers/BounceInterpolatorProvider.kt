package au.sjowl.lib.view.animations.interpolators.providers

import android.view.animation.BounceInterpolator

class BounceInterpolatorProvider : InterpolatorProvider {
    override fun floatInterpolator() = BounceInterpolator()
}