package au.sjowl.lib.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.bottomnav.FluidNavigationItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFluidBottombar()
    }

    private fun setupFluidBottombar() {
        fluidNavigationBar.items = listOf(
            FluidNavigationItem("chat", R.drawable.ic_chat_svg_xml),
            FluidNavigationItem("calendar", R.drawable.ic_calendar_svg_xml),
            FluidNavigationItem("profile", R.drawable.ic_profile_svg_xml),
            FluidNavigationItem("wallet", R.drawable.ic_wallet_svg_xml),
            FluidNavigationItem("", R.drawable.ic_search) // empty title if do not want to draw it
        )

        fluidNavigationBar.onItemSelected { index ->
            println("selected   $index")
        }
        fluidNavigationBar.onItemReselected { index ->
            println("reselected $index")
        }
        fluidNavigationBar.colorBubble = Color.parseColor("#0011B6")
        fluidNavigationBar.colorTintSelected = Color.parseColor("#fafafa")
        fluidNavigationBar.colorTintUnselected = Color.parseColor("#0011B6")
        fluidNavigationBar.colorTitle = Color.BLACK
    }
}