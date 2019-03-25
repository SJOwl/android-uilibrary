package au.sjowl.lib.ui.app.bottomnav

import android.graphics.Color
import android.os.Bundle
import android.view.View
import au.sjowl.lib.ui.app.BaseFragment
import au.sjowl.lib.ui.views.bottomnav.spread.SpreadNavigationItem
import au.sjowl.lib.ui.views.utils.colorCompat
import au.sjowl.lib.uxlibrary.R
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
                SpreadNavigationItem("chat", R.drawable.ic_chat_svg_xml, context.colorCompat(R.color.color1), context.colorCompat(R.color.color1_darker)),
                SpreadNavigationItem("calendar", R.drawable.ic_calendar_svg_xml, context.colorCompat(R.color.color2), context.colorCompat(R.color.color2_darker)),
                SpreadNavigationItem("profile", R.drawable.ic_profile_svg_xml, context.colorCompat(R.color.color3), context.colorCompat(R.color.color3_darker)),
                SpreadNavigationItem("wallet", R.drawable.ic_wallet_svg_xml, context.colorCompat(R.color.color4), context.colorCompat(R.color.color4_darker)),
                SpreadNavigationItem("search", R.drawable.ic_search, context.colorCompat(R.color.color5), context.colorCompat(R.color.color5_darker))
            )

            onItemSelected { index ->
                println("selected   $index")
            }
            onItemReselected { index ->
                println("reselected $index")
            }

            setBadgeCount(0, 25)
            setBadgeCount(1, 0)
            setBadgeCount(2, 3)
            setBadgeCount(3, 150)
            setBadgeCount(4, 12040)

            animationDuration = 180L

            colorTintUnselected = context.colorCompat(R.color.colorTextSelected)
            colorBadgeBackground = Color.RED
            colorBadgeText = Color.WHITE

            iconSize = context.dip(24)

            currentItemIndex = 3
        }

        root.onClick {
            spreadNavigationBar.setBadgeCount(2, Random.nextInt(0, 100))
        }
    }
}