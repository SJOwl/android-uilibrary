package au.sjowl.lib.view.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import au.sjowl.lib.uxlibrary.BuildConfig
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.bottomnav.NavFluidFragment
import au.sjowl.lib.view.app.bottomnav.NavRotationFragment
import au.sjowl.lib.view.app.bottomnav.NavSpreadFragment
import au.sjowl.lib.view.app.buttons.FabMenuCircularFragment
import au.sjowl.lib.view.app.buttons.FabMenuVerticalFragment
import au.sjowl.lib.view.app.buttons.SimpleButtonFragment
import au.sjowl.lib.view.app.buttons.SubmitButtonFragment
import au.sjowl.lib.view.app.charts.ChartsFragment
import au.sjowl.lib.view.app.gallery.home.HomeFragment
import au.sjowl.lib.view.app.test.touch.TestTouchesFragment
import au.sjowl.lib.view.app.transitions.TransitionsFragment

// todo 1 to fabs: https://github.com/Nightonke/BoomMenu
// todo 2 https://github.com/yavski/fab-speed-dial
class MainActivity : AppCompatActivity() {

    private val currentFragmentIndex = 13

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainNavigationFragment, testFragment())
            commit()
        }
    }

    private fun testFragment(): BaseFragment {
        if (BuildConfig.DEBUG) {
            return when (currentFragmentIndex) {
                1 -> ProgressFragment()
                2 -> NavFluidFragment()
                3 -> NavRotationFragment()
                4 -> NavSpreadFragment()
                5 -> SimpleButtonFragment()
                6 -> SubmitButtonFragment()
                7 -> FabMenuVerticalFragment()
                8 -> FabMenuCircularFragment()
                9 -> TransitionsFragment()
                10 -> ChartsFragment()
                11 -> ShadersFragment()
                12 -> TestTouchesFragment()
                13 -> CheckboxFragment()

                else -> HomeFragment()
            }
        }
        return HomeFragment()
    }
}

class XFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.layout_test

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
