package au.sjowl.lib.ui.app.base

import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R

class XFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_todo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}