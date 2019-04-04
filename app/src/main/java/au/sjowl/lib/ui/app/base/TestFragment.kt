package au.sjowl.lib.ui.app.base

import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.layout_test.*

class TestFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.layout_test

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoscrollTextView.isSelected = true
    }
}