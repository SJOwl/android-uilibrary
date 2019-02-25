package au.sjowl.lib.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.bottomnav.NavigationItem
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setupFluidBottombar()

        setupRotationBottombar()
    }

//    private fun setupFluidBottombar() {
//        with(fluidNavigationBar) {
//            items = listOf(
//                NavigationItem("chat", R.drawable.ic_chat_svg_xml),
//                NavigationItem("calendar", R.drawable.ic_calendar_svg_xml),
//                NavigationItem("profile", R.drawable.ic_profile_svg_xml),
//                NavigationItem("wallet", R.drawable.ic_wallet_svg_xml),
//                NavigationItem("", R.drawable.ic_search) // empty title if do not want to draw it
//            )
//
//            onItemSelected { index ->
//                println("selected   $index")
//            }
//            onItemReselected { index ->
//                println("reselected $index")
//            }
//            colorBubble = Color.parseColor("#0011B6")
//            colorTintSelected = Color.parseColor("#fafafa")
//            colorTintUnselected = Color.parseColor("#0011B6")
//            colorTitle = Color.BLACK
//        }
//    }

    private fun setupRotationBottombar() {
        with(rotNavigationBar) {
            items = listOf(
                NavigationItem("chat", R.drawable.ic_chat_svg_xml),
                NavigationItem("calendar", R.drawable.ic_calendar_svg_xml),
                NavigationItem("profile", R.drawable.ic_profile_svg_xml),
                NavigationItem("wallet", R.drawable.ic_wallet_svg_xml),
                NavigationItem("", R.drawable.ic_search) // empty title if do not want to draw it
            )
            currentItemIndex = 3

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

            animationDuration

            colorBubble = Color.BLUE
            colorBackground = Color.WHITE
            colorTintSelected = Color.BLUE
            colorTintUnselected = Color.LTGRAY
            colorBadgeBackground = Color.RED
            colorBadgeText = Color.WHITE

            iconSize = context.dip(24)
        }

        container.onClick {
            rotNavigationBar.setBadgeCount(2, Random.nextInt(0, 100))
        }
    }
}