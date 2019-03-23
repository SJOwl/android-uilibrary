package au.sjowl.lib.view.app.gallery.home

data class CategoryData(
    val name: String,
    @HomeFragment.Companion.Categories val fragmentId: Int
)