package au.sjowl.lib.view.app.buttons

import android.os.Bundle
import android.view.View
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.app.BaseFragment
import kotlinx.android.synthetic.main.fr_button_with_loader.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ButtonWithLoaderFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_button_with_loader

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        button.isEnabled = false
        button.onClick {
            //            context?.toast("button clicked!")
            println("button clicked!")
        }
    }
}