package au.sjowl.lib.ui.app.buttons.commands

interface MenuCommand {
    val drawableId: Int
    fun execute()
}