package au.sjowl.lib.view.app

import android.os.Bundle
import android.view.View
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.bottomnav.NavigationItem
import kotlinx.android.synthetic.main.fr_bottomnav_spread.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.random.Random

class NavSpreadFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_bottomnav_spread

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottombar()
    }

    private fun setupBottombar() {
        with(spreadNavigationBar) {
            items = listOf(
                NavigationItem("chat", R.drawable.ic_chat_svg_xml),
                NavigationItem("calendar", R.drawable.ic_calendar_svg_xml),
                NavigationItem("profile", R.drawable.ic_profile_svg_xml)
//                NavigationItem("wallet", R.drawable.ic_wallet_svg_xml),
//                NavigationItem("", R.drawable.ic_search)
            )
//            currentItemIndex = 3

            onItemSelected { index ->
                println("selected   $index")
            }
            onItemReselected { index ->
                println("reselected $index")
            }

            setBadgeCount(0, 25)
            setBadgeCount(1, 0)
            setBadgeCount(2, 3)
//            setBadgeCount(3, 150)
//            setBadgeCount(4, 12040)

            animationDuration

            colorBubble = android.graphics.Color.BLUE
            colorBackground = android.graphics.Color.WHITE
            colorTintSelected = android.graphics.Color.BLUE
            colorTintUnselected = android.graphics.Color.LTGRAY
            colorBadgeBackground = android.graphics.Color.RED
            colorBadgeText = android.graphics.Color.WHITE

            iconSize = context.dip(24)
        }

        root.onClick {
            spreadNavigationBar.setBadgeCount(2, Random.nextInt(0, 100))
        }
    }
}