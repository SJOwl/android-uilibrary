package au.sjowl.lib.view.app

import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R

class XFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.layout_test

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}