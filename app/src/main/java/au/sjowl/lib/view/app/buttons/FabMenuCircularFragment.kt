package au.sjowl.lib.view.app.buttons

import android.os.Bundle
import android.view.View
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.app.buttons.commands.CommandChat
import au.sjowl.lib.view.app.buttons.commands.CommandSearch
import au.sjowl.lib.view.app.buttons.commands.CommandWallet
import kotlinx.android.synthetic.main.fr_button_fab_menu_circular.*

class FabMenuCircularFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fr_button_fab_menu_circular

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