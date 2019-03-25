package au.sjowl.lib.ui.app.test.touch

import android.os.Bundle
import android.view.View
import au.sjowl.lib.ui.app.BaseFragment
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_test_touches.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

class TestTouchesFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_test_touches

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        group_dispatch.isChecked = false
        group_intercept.isChecked = false
        group_touch.isChecked = false
        group_dispatch.onCheckedChange { buttonView, isChecked -> group.rDispatch = isChecked }
        group_intercept.onCheckedChange { buttonView, isChecked -> group.rIntercept = isChecked }
        group_touch.onCheckedChange { buttonView, isChecked -> group.rTouch = isChecked }

        root_dispatch.isChecked = false
        root_intercept.isChecked = false
        root_touch.isChecked = false
        root_dispatch.onCheckedChange { buttonView, isChecked -> root.rDispatch = isChecked }
        root_intercept.onCheckedChange { buttonView, isChecked -> root.rIntercept = isChecked }
        root_touch.onCheckedChange { buttonView, isChecked -> root.rTouch = isChecked }

        view_dispatch.isChecked = false
        view_touch.isChecked = false
        view_dispatch.onCheckedChange { buttonView, isChecked -> tview.rDispatch = isChecked }
        view_touch.onCheckedChange { buttonView, isChecked -> tview.rTouch = isChecked }
    }
}