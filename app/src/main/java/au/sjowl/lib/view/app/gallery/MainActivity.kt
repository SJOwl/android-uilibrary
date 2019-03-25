package au.sjowl.lib.view.app.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.bottomnav.NavFluidFragment
import au.sjowl.lib.view.app.bottomnav.NavRotationFragment
import au.sjowl.lib.view.app.bottomnav.NavSpreadFragment
import au.sjowl.lib.view.app.buttons.FabMenuCircularFragment
import au.sjowl.lib.view.app.buttons.FabMenuVerticalFragment
import au.sjowl.lib.view.app.gallery.home.CategoryData
import au.sjowl.lib.view.app.gallery.home.HomeFragment

// todo 1 to fabs: https://github.com/Nightonke/BoomMenu
// todo 2 https://github.com/yavski/fab-speed-dial
class MainActivity : AppCompatActivity() {

    private val currentFragment = Screens.MAIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainNavigationFragment, testFragment())
            commit()
        }
    }

    private fun testFragment(): Fragment {
        return Screens.fragmentFromId(currentFragment.fragmentId)
    }
}

object Screens {
    var key = 0

    val MAIN = CategoryData("Home", key++)
    val NAVBAR_FLUID = CategoryData("Fluid", key++)
    val NAVBAR_ROTATION = CategoryData("Rotations", key++)
    val NAVBAR_SPREAD = CategoryData("Spread", key++)
    val FAB_CIRCULAR = CategoryData("Circular", key++)
    val FAB_VERTICAL = CategoryData("Vertical", key++)

    val LIST_BUTTONS = CategoryData("Buttons", key++)
    val LIST_BOTTOMBARS = CategoryData("Bottombars", key++)
    val LIST_CHARTS = CategoryData("Charts", key++)
    val LIST_TRANSITIONS = CategoryData("Transitions", key++)
    val LIST_PROGRESSBAR = CategoryData("Progressbar", key++)
    val LIST_FAB = CategoryData("FAB", key++)

    fun fragmentFromId(id: Int): Fragment {
        return when (id) {
            MAIN.fragmentId -> HomeFragment()
            NAVBAR_FLUID.fragmentId -> NavFluidFragment()
            NAVBAR_ROTATION.fragmentId -> NavRotationFragment()
            NAVBAR_SPREAD.fragmentId -> NavSpreadFragment()
            FAB_CIRCULAR.fragmentId -> FabMenuCircularFragment()
            FAB_VERTICAL.fragmentId -> FabMenuVerticalFragment()

            LIST_BUTTONS.fragmentId -> CategoryButtonsFragment()
            LIST_BOTTOMBARS.fragmentId -> CategoryBottombarsFragment()
            LIST_CHARTS.fragmentId -> CategoryChartsFragment()
            LIST_TRANSITIONS.fragmentId -> CategoryTransitionsFragment()
            LIST_PROGRESSBAR.fragmentId -> CategoryProgressBarFragment()
            LIST_FAB.fragmentId -> CategoryFabFragment()
            else -> throw IllegalStateException("")
        }
    }
}