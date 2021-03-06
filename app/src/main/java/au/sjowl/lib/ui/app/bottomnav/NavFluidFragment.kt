package au.sjowl.lib.ui.app.bottomnav

import android.graphics.Color
import android.os.Bundle
import android.view.View
import au.sjowl.lib.ui.app.base.BaseFragment
import au.sjowl.lib.ui.views.bottomnav.fluid.NavigationItem
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.fr_bottomnav_fluid.*
import org.jetbrains.anko.dip

class NavFluidFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_bottomnav_fluid

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFluidBottombar()
    }

    private fun setupFluidBottombar() {
        with(fluidNavigationBar) {
            items = listOf(
                NavigationItem("chat", R.drawable.ic_chat_svg_xml),
                NavigationItem("calendar", R.drawable.ic_calendar_svg_xml),
                NavigationItem("profile", R.drawable.ic_profile_svg_xml),
                NavigationItem("wallet", R.drawable.ic_wallet_svg_xml),
                NavigationItem("", R.drawable.ic_search) // empty title if do not want to draw it
            )

            onItemSelected { index ->
                println("selected   $index")
            }
            onItemReselected { index ->
                println("reselected $index")
            }
            colorBubble = Color.parseColor("#0011B6")
            colorBackground = Color.parseColor("#fafafa")
            colorTintSelected = Color.parseColor("#fafafa")
            colorTintUnselected = Color.parseColor("#0011B6")
            colorTitle = Color.parseColor("#0011B6")
            animationDuration = 200L
            iconSize = context.dip(24)

            currentItemIndex = 1
        }
    }
}