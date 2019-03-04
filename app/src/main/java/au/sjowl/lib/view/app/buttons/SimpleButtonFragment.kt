package au.sjowl.lib.view.app.buttons

import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import kotlinx.android.synthetic.main.fr_button_simple.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class SimpleButtonFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_button_simple

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.onClick {
            context?.toast("button clicked!")
            println("button clicked!")
        }
    }

    override fun onResume() {
        super.onResume()
        button.isEnabled = false
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000)
            button.isEnabled = true
        }
    }
}