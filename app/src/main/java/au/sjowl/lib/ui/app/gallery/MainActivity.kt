package au.sjowl.lib.ui.app.gallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import au.sjowl.lib.ui.app.Screens
import au.sjowl.lib.uxlibrary.R

// todo 1 to fabs: https://github.com/Nightonke/BoomMenu
// todo 2 https://github.com/yavski/fab-speed-dial
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainNavigationFragment, testFragment())
            commit()
        }
    }

    private fun testFragment(): Fragment {
        return Screens.fragmentFromId(Screens.developmentScreen)
    }
}