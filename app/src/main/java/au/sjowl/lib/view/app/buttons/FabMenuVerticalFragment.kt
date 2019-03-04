package au.sjowl.lib.view.app.buttons

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.buttons.fab.vertical.FabMenuItem
import kotlinx.android.synthetic.main.fr_button_fab_menu_vertical.*

class FabMenuVerticalFragment : BaseFragment() {
    override val layoutId: Int get() = R.layout.fr_button_fab_menu_vertical

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colorTint = ContextCompat.getColor(this.context!!, R.color.fab_menu_tint)
        fab.fabIconId = R.drawable.ic_add
        fab.setMenuItems(
            arrayListOf(
                FabMenuItem(R.drawable.ic_search, colorTint),
                FabMenuItem(R.drawable.ic_chat_svg_xml, colorTint),
                FabMenuItem(R.drawable.ic_wallet_svg_xml, colorTint)
            )
        )
        fab.onItemSelected { itemIndex: Int ->
            println("selected # $itemIndex")
        }
    }
}