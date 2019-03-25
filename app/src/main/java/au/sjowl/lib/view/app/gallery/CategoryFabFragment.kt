package au.sjowl.lib.view.app.gallery

import au.sjowl.lib.view.app.gallery.Screens.FAB_CIRCULAR
import au.sjowl.lib.view.app.gallery.Screens.FAB_VERTICAL
import au.sjowl.lib.view.app.gallery.home.CategoryData

class CategoryFabFragment : ListFragment() {
    override val screens: List<CategoryData> = listOf(
        FAB_CIRCULAR,
        FAB_VERTICAL
    )
}