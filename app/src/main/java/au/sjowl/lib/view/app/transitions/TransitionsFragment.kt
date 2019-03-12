package au.sjowl.lib.view.app.transitions

import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import kotlinx.android.synthetic.main.fr_transition_top_menu.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick

class TransitionsFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_transition_top_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menu.onClick {
            println("click on top")
            menu.layoutParams.height = context!!.dip(200)
            root.requestLayoutWithTransition()
        }
        menu.onMenuItemClick { id ->
            println("click on $id")
        }

        content.onClick {
            println("click on content")
            menu.layoutParams.height = context!!.dip(56)
            root.requestLayoutWithTransition()
        }

        childFragmentManager.beginTransaction().apply {
            replace(content.id, ContentFragment())
            commit()
        }
    }
}