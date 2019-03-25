package au.sjowl.lib.ui.views.animations.interpolators

class SinusDissipationInterpolator(
    private var amplitude: Double = 0.9,
    private var frequency: Double = 3.0
) : android.view.animation.Interpolator {
    override fun getInterpolation(x: Float): Float {
        return (amplitude * Math.sin(Math.PI * frequency * x) * (1 - x) + 1).toFloat()
    }
}