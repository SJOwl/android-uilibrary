package au.sjowl.lib.ui.app.gallery

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import au.sjowl.lib.ui.app.Screens
import au.sjowl.lib.ui.app.base.BaseFragment
import au.sjowl.lib.ui.app.gallery.home.CategoryAdapter
import au.sjowl.lib.ui.app.gallery.home.CategoryHolderListener
import au.sjowl.lib.ui.app.gallery.home.ScreenData
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_home.*

class ListFragment : BaseFragment() {

//    abstract val screens: List<ScreenData>

    override val layoutId: Int get() = R.layout.fr_home

    val categoryAdapter = CategoryAdapter(object : CategoryHolderListener {
        override fun onClick(data: ScreenData) {
            println("click on ${data.name}")
            val fragment = Screens.fragmentFromId(data.fragmentId)
            activity?.run {
                supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(R.anim.fr_transition_enter, R.anim.fr_transition_exit,
                        R.anim.fr_transition_enter, R.anim.fr_transition_exit)
                    replace(R.id.mainNavigationFragment, fragment, data.name)
                    addToBackStack(data.name)
                    commit()
                }
            }
        }
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(recyclerView) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = categoryAdapter
            addItemDecoration(au.sjowl.lib.ui.views.recycler.MiddleDividerItemDecoration(context))
        }
        categoryAdapter.items = arguments!!.getIntArray(KEY_LIST)!!
            .map { Screens.fromKey(it) }
            .sortedBy { it.name }
    }

    companion object {
        private val KEY_LIST = "KEY_LIST"
        fun createArguments(items: IntArray) = ListFragment().apply {
            arguments = bundleOf().apply {
                putIntArray(KEY_LIST, items)
            }
        }
    }
}