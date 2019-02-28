package au.sjowl.lib.view.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.app.bottomnav.NavFluidFragment
import au.sjowl.lib.view.app.bottomnav.NavRotationFragment
import au.sjowl.lib.view.app.bottomnav.NavSpreadFragment
import au.sjowl.lib.view.app.buttons.SimpleButtonFragment
import au.sjowl.lib.view.app.buttons.SubmitButtonFragment

class MainActivity : AppCompatActivity() {

    private val currentFragmentIndex = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainNavigationFragment, testFragment())
            commit()
        }
    }

    private fun testFragment(): BaseFragment {
        return when (currentFragmentIndex) {
            0 -> ProgressFragment()
            1 -> NavFluidFragment()
            2 -> NavRotationFragment()
            3 -> NavSpreadFragment()
            4 -> SimpleButtonFragment()
            5 -> SubmitButtonFragment()

            else -> HomeFragment()
        }
    }
}

class HomeFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.layout_test

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}