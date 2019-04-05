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
            particlesParams = LinkedParticleView.ParticlesParams(
                color = Color.parseColor("#00D37F"),
                count = 80,
                radiusMin = 5,
                radiusMax = 10,
                velocityX = 20,
                velocityY = 20
            )
            applyChanges()
        }
    }

    override fun onPause() {
        super.onPause()
        linkedParticlesView.stopThread()
    }
}
