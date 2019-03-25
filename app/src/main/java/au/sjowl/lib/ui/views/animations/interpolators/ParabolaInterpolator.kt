package au.sjowl.lib.ui.views.animations.interpolators

class ParabolaInterpolator : android.view.animation.Interpolator {
    override fun getInterpolation(x: Float): Float {
        return -(2 * x - 1) * (2 * x - 1) + 2
    }
}