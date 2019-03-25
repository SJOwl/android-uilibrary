package au.sjowl.lib.view.app.gallery

import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.app.gallery.home.CategoryAdapter
import au.sjowl.lib.view.app.gallery.home.CategoryData
import au.sjowl.lib.view.app.gallery.home.CategoryHolderListener
import kotlinx.android.synthetic.main.fr_home.*

abstract class ListFragment : BaseFragment() {

    abstract val screens: List<CategoryData>

    override val layoutId: Int get() = R.layout.fr_home

    val categoryAdapter = CategoryAdapter(object : CategoryHolderListener {
        override fun onClick(data: CategoryData) {
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
            addItemDecoration(au.sjowl.lib.view.recycler.MiddleDividerItemDecoration(context))
        }
        categoryAdapter.items = screens
    }
}