package au.sjowl.lib.view.app.buttons.commands

interface MenuCommand {
    val drawableId: Int
    fun execute()
}