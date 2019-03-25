package au.sjowl.lib.ui.app.gallery

import au.sjowl.lib.ui.app.bottomnav.NavFluidFragment
import au.sjowl.lib.ui.app.bottomnav.NavRotationFragment
import au.sjowl.lib.ui.app.bottomnav.NavSpreadFragment
import au.sjowl.lib.ui.app.buttons.FabMenuCircularFragment
import au.sjowl.lib.ui.app.buttons.FabMenuVerticalFragment
import au.sjowl.lib.ui.app.charts.TelegramChartsFragment
import au.sjowl.lib.ui.app.gallery.home.ScreenData
import au.sjowl.lib.ui.app.transitions.TransitionsFragment

object Screens {
    var key = 0

    val MAIN = key++
    val NAVBAR_FLUID = key++
    val NAVBAR_ROTATION = key++
    val NAVBAR_SPREAD = key++
    val FAB_CIRCULAR = key++
    val FAB_VERTICAL = key++
    val CHART_TELEGRAM = key++
    val LIST_BUTTONS = key++
    val LIST_BOTTOMBARS = key++
    val LIST_CHARTS = key++
    val LIST_TRANSITIONS = key++
    val LIST_PROGRESSBAR = key++
    val LIST_FAB = key++
    val LIST_CHECKBOX = key++
    val TRANSITIONS_TOP_MENU = key++

    //    val developmentScreen = MAIN
    val developmentScreen = TRANSITIONS_TOP_MENU

    val screens = listOf(
        ScreenData("Home", MAIN) { ListFragment.createArguments(intArrayOf(LIST_BUTTONS, LIST_BOTTOMBARS, LIST_CHARTS, LIST_TRANSITIONS, LIST_PROGRESSBAR, LIST_FAB, LIST_CHECKBOX)) },
        ScreenData("Fluid", NAVBAR_FLUID) { NavFluidFragment() },
        ScreenData("Rotations", NAVBAR_ROTATION) { NavRotationFragment() },
        ScreenData("Spread", NAVBAR_SPREAD) { NavSpreadFragment() },
        ScreenData("Circular", FAB_CIRCULAR) { FabMenuCircularFragment() },
        ScreenData("Vertical", FAB_VERTICAL) { FabMenuVerticalFragment() },
        ScreenData("Telegram chart", CHART_TELEGRAM) { TelegramChartsFragment() },
        ScreenData("Top menu", TRANSITIONS_TOP_MENU) { TransitionsFragment() },
        ScreenData("Buttons", LIST_BUTTONS) { CategoryButtonsFragment() },
        ScreenData("Bottombars", LIST_BOTTOMBARS) { ListFragment.createArguments(intArrayOf(NAVBAR_FLUID, NAVBAR_ROTATION, NAVBAR_SPREAD)) },
        ScreenData("Charts", LIST_CHARTS) { ListFragment.createArguments(intArrayOf(CHART_TELEGRAM)) },
        ScreenData("Transitions", LIST_TRANSITIONS) { ListFragment.createArguments(intArrayOf(TRANSITIONS_TOP_MENU)) },
        ScreenData("Progressbar", LIST_PROGRESSBAR) { CategoryProgressBarFragment() },
        ScreenData("FAB", LIST_FAB) { ListFragment.createArguments(intArrayOf(FAB_CIRCULAR, FAB_VERTICAL)) },
        ScreenData("Checkbox", LIST_CHECKBOX) { CategoryCheckboxFragment() }
    )

    fun fragmentFromId(key: Int) = fromKey(key).fragment()

    fun fromKey(key: Int): ScreenData = screens.first { it.fragmentId == key }
}