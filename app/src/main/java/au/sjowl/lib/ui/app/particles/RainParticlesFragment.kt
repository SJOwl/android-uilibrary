package au.sjowl.lib.ui.app.particles

import android.graphics.Color
import android.os.Bundle
import android.view.View
import au.sjowl.lib.ui.app.base.BaseFragment
import au.sjowl.lib.ui.views.particles.RainParticleView
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_particles_rain.*

class RainParticlesFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_particles_rain

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rainParticlesView) {
            background = Color.parseColor("#3D3D3D")
//            bgDrawable = context.getDrawable(R.drawable.bg)
            particlesParams = RainParticleView.ParticlesParams(
                velocityMin = 20,
                velocityMax = 40,
                angleDegrees = 70,
                width = 7f,
                color = Color.parseColor("#ffffff"),
                count = 1000
            )
            applyChanges()
        }
    }

    override fun onPause() {
        super.onPause()
        rainParticlesView.stopThread()
    }
}