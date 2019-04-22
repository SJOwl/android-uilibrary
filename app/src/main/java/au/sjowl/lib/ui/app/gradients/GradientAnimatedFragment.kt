package au.sjowl.lib.ui.app.gradients

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import au.sjowl.lib.ui.app.base.BaseFragment
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_gradient_anim.*

class GradientAnimatedFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_gradient_anim

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        with(root.background as AnimationDrawable) {
            setEnterFadeDuration(10)
            setExitFadeDuration(500)
            start()
        }
    }
}