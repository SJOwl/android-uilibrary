package au.sjowl.lib.view.app.buttons.commands

import au.sjowl.lib.uxlibrary.R

class CommandChat : MenuCommand {
    override val drawableId: Int get() = R.drawable.ic_chat_svg_xml
    override fun execute() {
        println("command chat")
    }
}