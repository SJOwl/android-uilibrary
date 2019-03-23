package au.sjowl.lib.view.app.gallery.home

import android.os.Bundle
import android.view.View
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.app.gallery.CategoryBottombarsFragment
import au.sjowl.lib.view.app.gallery.CategoryButtonsFragment
import au.sjowl.lib.view.app.gallery.CategoryChartsFragment
import au.sjowl.lib.view.app.gallery.CategoryProgressBarFragment
import au.sjowl.lib.view.app.gallery.CategoryTransitionsFragment
import kotlinx.android.synthetic.main.fr_home.*

class HomeFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fr_home

    private val categories = listOf(
        CategoryData("Buttons", BUTTONS),
        CategoryData("Bottombars", BOTTOMBARS),
        CategoryData("Charts", CHARTS),
        CategoryData("Transitions", TRANSLATIONS),
        CategoryData("Progressbar", PROGRESSBAR)
    )

    private val adapter = CategoryAdapter(object : CategoryHolderListener {
        override fun onClick(data: CategoryData) {
            println("click on ${data.name}")
            val fragment = fragmentFromId(data.fragmentId)
            activity?.run {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.mainNavigationFragment, fragment, data.name)
                    addToBackStack(data.name)
                    // todo set animation
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    commit()
                }
            }
        }
    }).apply {
        items = categories
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun fragmentFromId(id: Int): Fragment {
        return when (id) {
            BUTTONS -> CategoryButtonsFragment()
            BOTTOMBARS -> CategoryBottombarsFragment()
            CHARTS -> CategoryChartsFragment()
            TRANSLATIONS -> CategoryTransitionsFragment()
            PROGRESSBAR -> CategoryProgressBarFragment()
            else -> throw IllegalArgumentException("")
        }
    }

    companion object {
        @IntDef(BUTTONS, BOTTOMBARS, CHARTS, TRANSLATIONS, PROGRESSBAR)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Categories

        const val BUTTONS = 0
        const val BOTTOMBARS = 1
        const val CHARTS = 2
        const val TRANSLATIONS = 3
        const val PROGRESSBAR = 4
    }
}