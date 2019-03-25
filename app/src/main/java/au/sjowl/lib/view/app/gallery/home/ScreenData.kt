package au.sjowl.lib.view.app.gallery.home

import androidx.fragment.app.Fragment

class ScreenData(
    val name: String,
    val fragmentId: Int,
    val fragment: (() -> Fragment)
)