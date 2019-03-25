package au.sjowl.lib.view.app.gallery

import au.sjowl.lib.view.app.gallery.Screens.NAVBAR_FLUID
import au.sjowl.lib.view.app.gallery.Screens.NAVBAR_ROTATION
import au.sjowl.lib.view.app.gallery.Screens.NAVBAR_SPREAD
import au.sjowl.lib.view.app.gallery.home.CategoryData

class CategoryBottombarsFragment : ListFragment() {
    override val screens: List<CategoryData> = listOf(
        NAVBAR_FLUID,
        NAVBAR_ROTATION,
        NAVBAR_SPREAD
    )
}
