package au.sjowl.lib.ui.app.gallery

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import au.sjowl.lib.ui.app.base.BaseFragment
import au.sjowl.lib.ui.views.checkbox.RoundTitledCheckbox
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_checkbox.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.margin
import org.jetbrains.anko.wrapContent

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

        with(borderedCheckbox0) {
            checked = false
            color = Color.parseColor("#3DC23F")
            onCheckedChangedListener { checked -> println("checked $checked #2") }
        }
        with(borderedCheckbox1) {
            checked = true
            color = Color.parseColor("#F34C44")
            onCheckedChangedListener { checked -> println("checked $checked #3") }
        }

        arrayOf(
            "Android",
            "iOS",
            "Web",
            "OSX",
            "Desktop",
            "Other",
            "dp"
        ).forEach { name ->
            linesContainer.addView(RoundTitledCheckbox(context!!).apply {
                layoutParams = ViewGroup.MarginLayoutParams(wrapContent, wrapContent).apply {
                    margin = context.dip(8)
                }
                val i = name.length
                color = Color.parseColor("#F3${i}C4$i")
                checked = true
                title = name
                onCheckedChangedListener { checked -> println("checked $checked #3") }
            })
        }
    }
}
