package au.sjowl.lib.view.app.buttons

import android.os.Bundle
import android.view.View
import au.sjowl.lib.twolinestextview.R
import au.sjowl.lib.view.app.BaseFragment
import kotlinx.android.synthetic.main.fr_button_fab_menu_vertical.*

class FabMenuVerticalFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fr_button_fab_menu_vertical

    private val menuCommands = listOf(
        CommandSearch(),
        CommandChat(),
        CommandWallet()
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.fabIconId = R.drawable.ic_add
        fab.setMenuItems(
            menuCommands.map { it.drawableId }
        )
        fab.onItemSelected { itemIndex: Int ->
            menuCommands[itemIndex].execute()
        }
    }
}

interface MenuCommand {
    val drawableId: Int
    fun execute()
}

class CommandSearch : MenuCommand {
    override val drawableId: Int get() = R.drawable.ic_search
    override fun execute() {
        println("command search")
    }
}

class CommandChat : MenuCommand {
    override val drawableId: Int get() = R.drawable.ic_chat_svg_xml
    override fun execute() {
        println("command chat")
    }
}

class CommandWallet : MenuCommand {
    override val drawableId: Int get() = R.drawable.ic_wallet_svg_xml
    override fun execute() {
        println("command wallet")
    }
}