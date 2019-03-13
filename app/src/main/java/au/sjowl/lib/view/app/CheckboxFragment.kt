package au.sjowl.lib.view.app

import android.graphics.Color
import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_checkbox.*

class CheckboxFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_checkbox

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(checkbox0) {
            checked = true
            color = Color.GREEN
            onCheckedChangedListener { checked -> println("checked $checked #0") }
        }

        with(checkbox1) {
            checked = true
            color = Color.RED
            onCheckedChangedListener { checked -> println("checked $checked #1") }
        }
    }
}
