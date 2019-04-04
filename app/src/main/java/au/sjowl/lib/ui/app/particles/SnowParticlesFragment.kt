package au.sjowl.lib.ui.app.particles

import android.graphics.Color
import android.os.Bundle
import android.view.View
import au.sjowl.lib.ui.app.base.BaseFragment
import au.sjowl.lib.ui.views.particles.SnowParticleView
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_particles_snow.*

class SnowParticlesFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_particles_snow

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(snowParticlesView) {
            background = Color.parseColor("#07005F")
            color = Color.parseColor("#FCFCFC")
            count = 500
            particlesParams = ParticlesSnowParams()
            applyChanges()
        }
    }

    override fun onPause() {
        super.onPause()
        snowParticlesView.stopThread()
    }

    class ParticlesSnowParams : SnowParticleView.ParticlesParams(
        radiusMin = 1,
        radiusMax = 15,
        velocityXMin = 3,
        velocityXMax = 10,
        velocityYMin = 10,
        velocityYMax = 20
    )
}