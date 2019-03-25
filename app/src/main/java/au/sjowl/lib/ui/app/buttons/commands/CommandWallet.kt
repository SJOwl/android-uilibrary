package au.sjowl.lib.ui.app.buttons.commands

import au.sjowl.lib.uxlibrary.R

class CommandWallet : MenuCommand {
    override val drawableId: Int get() = R.drawable.ic_wallet_svg_xml
    override fun execute() {
        println("command wallet")
    }
}