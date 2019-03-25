package au.sjowl.lib.ui.app.buttons.commands

import au.sjowl.lib.uxlibrary.R

class CommandSearch : MenuCommand {
    override val drawableId: Int get() = R.drawable.ic_search
    override fun execute() {
        println("command search")
    }
}