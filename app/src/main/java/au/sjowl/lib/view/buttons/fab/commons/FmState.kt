package au.sjowl.lib.view.buttons.fab.commons

abstract class FmState {
    abstract val properties: Map<Int, Any>

    companion object {
        const val MULT = 0
        const val COLOR_FAB = 1
        const val COLOR_FAB_ICON = 2
        const val COLOR_BG = 3
    }
}