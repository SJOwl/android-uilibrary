package au.sjowl.lib.view.bottomnav.interpolators

class ParabolaInterpolator : android.view.animation.Interpolator {
    override fun getInterpolation(x: Float): Float {
        return -(2 * x - 1) * (2 * x - 1) + 2
    }
}