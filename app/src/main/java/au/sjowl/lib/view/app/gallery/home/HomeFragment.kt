package au.sjowl.lib.view.app.gallery.home

import au.sjowl.lib.view.app.gallery.ListFragment
import au.sjowl.lib.view.app.gallery.Screens.LIST_BOTTOMBARS
import au.sjowl.lib.view.app.gallery.Screens.LIST_BUTTONS
import au.sjowl.lib.view.app.gallery.Screens.LIST_CHARTS
import au.sjowl.lib.view.app.gallery.Screens.LIST_CHECKBOX
import au.sjowl.lib.view.app.gallery.Screens.LIST_FAB
import au.sjowl.lib.view.app.gallery.Screens.LIST_PROGRESSBAR
import au.sjowl.lib.view.app.gallery.Screens.LIST_TRANSITIONS

class HomeFragment : ListFragment() {

    override val screens = listOf(
        LIST_BUTTONS,
        LIST_BOTTOMBARS,
        LIST_CHARTS,
        LIST_TRANSITIONS,
        LIST_PROGRESSBAR,
        LIST_FAB,
        LIST_CHECKBOX
    ).sortedBy { it.name }
}