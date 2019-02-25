package au.sjowl.lib.view.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import au.sjowl.lib.twolinestextview.R

class MainActivity : AppCompatActivity() {

    private val currentFragmentIndex = 1

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