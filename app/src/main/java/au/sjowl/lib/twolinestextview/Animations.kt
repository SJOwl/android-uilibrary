package au.sjowl.lib.twolinestextview

import android.graphics.drawable.Animatable
import android.view.View
import android.view.animation.ScaleAnimation
import android.widget.ImageView

fun View.runScaleAnimation(scaleFrom: Float = 0.5f, scaleTo: Float = 1.0f, duration: Long = 250L) {
    startAnimation(ScaleAnimation(scaleFrom, scaleTo, scaleFrom, scaleTo, width / 2f, height / 2f).apply {
        interpolator = SinusDissipationInterpolator()
        this.duration = duration
    })
}

class SinusDissipationInterpolator(
    private var amplitude: Double = 0.9,
    private var frequency: Double = 3.0
) : android.view.animation.Interpolator {
    override fun getInterpolation(x: Float): Float {
        return (amplitude * Math.sin(Math.PI * frequency * x) * (1 - x) + 1).toFloat()
    }
}

fun AnimateImageView(v: ImageView) {
    var b = true
    val cursor = v.context.resources.getDrawable(R.drawable.search_reverse)
    val searchIcon = v.context.resources.getDrawable(R.drawable.search_anim)

    if (b)
        v.setImageDrawable(cursor)
    else
        v.setImageDrawable(searchIcon)
    (v.drawable as Animatable).start()
    b = !b
}