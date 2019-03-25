package au.sjowl.lib.view.app.gallery

import android.graphics.Color
import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import kotlinx.android.synthetic.main.fr_checkbox.*

class CategoryCheckboxFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_checkbox

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(checkbox0) {
            checked = true
            color = Color.parseColor("#3DC23F")
            onCheckedChangedListener { checked -> println("checked $checked #0") }
        }

        with(checkbox1) {
            checked = false
            color = Color.parseColor("#F34C44")
            onCheckedChangedListener { checked -> println("checked $checked #1") }
        }
    }
}