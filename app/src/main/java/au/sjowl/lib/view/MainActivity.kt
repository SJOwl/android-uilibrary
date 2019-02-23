package au.sjowl.lib.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.bottomnav.FluidNavigationItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fluidNavigationBar.items = listOf(
            FluidNavigationItem("chat", R.drawable.ic_chat_svg_xml),
            FluidNavigationItem("calendar", R.drawable.ic_calendar_svg_xml),
            FluidNavigationItem("profile", R.drawable.ic_profile_svg_xml),
            FluidNavigationItem("wallet", R.drawable.ic_wallet_svg_xml),
            FluidNavigationItem("search", R.drawable.ic_search)
        )

        fluidNavigationBar.onItemSelected { index ->
            println("selected   $index")
        }
        fluidNavigationBar.onItemReselected { index ->
            println("reselected $index")
        }
    }
}