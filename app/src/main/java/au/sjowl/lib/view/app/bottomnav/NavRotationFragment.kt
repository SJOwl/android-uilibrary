package au.sjowl.lib.view.app.bottomnav

import android.graphics.Color
import android.os.Bundle
import android.view.View
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.bottomnav.rotation.RotationNavItem
import au.sjowl.lib.view.utils.colorCompat
import kotlinx.android.synthetic.main.fr_bottomnav_rotation.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.random.Random

class NavRotationFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_bottomnav_rotation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRotationBottombar()
    }

    private fun setupRotationBottombar() {
        with(rotNavigationBar) {
            items = listOf(
                RotationNavItem("chat", R.drawable.ic_chat_svg_xml, context.colorCompat(R.color.color1_darker)),
                RotationNavItem("calendar", R.drawable.ic_calendar_svg_xml, context.colorCompat(R.color.color2_darker)),
                RotationNavItem("profile", R.drawable.ic_profile_svg_xml, context.colorCompat(R.color.color3_darker)),
                RotationNavItem("wallet", R.drawable.ic_wallet_svg_xml, context.colorCompat(R.color.color4_darker)),
                RotationNavItem("", R.drawable.ic_search, context.colorCompat(R.color.color5_darker))
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

            animationDuration = 250L

            colorBubble = Color.BLUE
            colorTintUnselected = Color.LTGRAY
            colorBadgeBackground = Color.RED
            colorBadgeText = Color.WHITE

            iconSize = context.dip(24)

            currentItemIndex = 3
        }

        container.onClick {
            rotNavigationBar.setBadgeCount(2, Random.nextInt(0, 100))
        }
    }
}