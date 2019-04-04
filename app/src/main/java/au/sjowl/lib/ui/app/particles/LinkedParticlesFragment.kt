package au.sjowl.lib.ui.app.particles

import android.graphics.Color
import android.os.Bundle
import android.view.View
import au.sjowl.lib.ui.app.base.BaseFragment
import au.sjowl.lib.ui.views.particles.LinkedParticleView
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_linked_particles.*

class LinkedParticlesFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_linked_particles

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(linkedParticlesView) {
            background = Color.parseColor("#FDFFFE")
            color = Color.parseColor("#00D37F")
            count = 50
            particlesParams = ParticlesLinksParams(
                radiusMin = 5,
                radiusMax = 10,
                velocity = 5,
                radiusToConnect = 280
            )
            applyChanges()
        }
    }

    override fun onPause() {
        super.onPause()
        linkedParticlesView.stopThread()
    }

    class ParticlesLinksParams(
        radiusMin: Int = 1,
        radiusMax: Int = 15,
        velocity: Int = 20,
        radiusToConnect: Int = 150
    ) : LinkedParticleView.ParticlesParams(
        radiusMin = radiusMin,
        radiusMax = radiusMax,
        velocityXMin = -velocity,
        velocityXMax = velocity,
        velocityYMin = -velocity,
        velocityYMax = velocity,
        radiusToConnect = radiusToConnect
    )
}
