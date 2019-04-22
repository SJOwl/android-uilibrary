package au.sjowl.lib.ui.app

import au.sjowl.lib.ui.app.bottomnav.NavFluidFragment
import au.sjowl.lib.ui.app.bottomnav.NavRotationFragment
import au.sjowl.lib.ui.app.bottomnav.NavSpreadFragment
import au.sjowl.lib.ui.app.buttons.FabMenuCircularFragment
import au.sjowl.lib.ui.app.buttons.FabMenuVerticalFragment
import au.sjowl.lib.ui.app.gallery.CategoryButtonsFragment
import au.sjowl.lib.ui.app.gallery.CategoryCheckboxFragment
import au.sjowl.lib.ui.app.gallery.CategoryProgressBarFragment
import au.sjowl.lib.ui.app.gallery.ListFragment
import au.sjowl.lib.ui.app.gallery.home.ScreenData
import au.sjowl.lib.ui.app.gradients.GradientAnimatedFragment
import au.sjowl.lib.ui.app.particles.LinkedParticlesFragment
import au.sjowl.lib.ui.app.particles.RainParticlesFragment
import au.sjowl.lib.ui.app.particles.SnowParticlesFragment
import au.sjowl.lib.ui.app.transitions.TransitionsFragment
import au.sjowl.lib.view.charts.telegram.fragment.ChartsFragment

object Screens {
    var key = 0

    val MAIN = key++
    val LIST_NAVBAR = key++
    /* -> */val NAVBAR_FLUID = key++
    /* -> */val NAVBAR_ROTATION = key++
    /* -> */val NAVBAR_SPREAD = key++
    val LIST_FAB = key++
    /* -> */val FAB_CIRCULAR = key++
    /* -> */val FAB_VERTICAL = key++

    val LIST_CHARTS = key++
    /* -> */val CHART_TELEGRAM = key++

    val LIST_PARTICLES = key++
    /* -> */val PARTICLES_LINKED = key++
    /* -> */val PARTICLES_SNOW = key++
    /* -> */val PARTICLES_RAIN = key++

    val LIST_BUTTONS = key++
    val LIST_PROGRESSBAR = key++
    val LIST_CHECKBOX = key++

    val LIST_TRANSITIONS = key++
    /* -> */val TRANSITIONS_TOP_MENU = key++

    val LIST_GRADIENTS = key++
    /* -> */val GRADIENT_ANIMATED = key++

    val developmentScreen = MAIN

    val screens = listOf(
        ScreenData("Home", MAIN) {
            ListFragment.createArguments(intArrayOf(
                LIST_BUTTONS,
                LIST_NAVBAR,
                LIST_CHARTS,
                LIST_TRANSITIONS,
                LIST_PROGRESSBAR,
                LIST_FAB,
                LIST_CHECKBOX,
                LIST_PARTICLES
            ))
        },

        ScreenData("Bottombars", LIST_NAVBAR) {
            ListFragment.createArguments(intArrayOf(
                NAVBAR_FLUID,
                NAVBAR_ROTATION,
                NAVBAR_SPREAD
            ))
        },
        ScreenData("Fluid", NAVBAR_FLUID) { NavFluidFragment() },
        ScreenData("Rotations", NAVBAR_ROTATION) { NavRotationFragment() },
        ScreenData("Spread", NAVBAR_SPREAD) { NavSpreadFragment() },

        ScreenData("FAB", LIST_FAB) {
            ListFragment.createArguments(intArrayOf(
                FAB_CIRCULAR,
                FAB_VERTICAL
            ))
        },
        ScreenData("Circular", FAB_CIRCULAR) { FabMenuCircularFragment() },
        ScreenData("Vertical", FAB_VERTICAL) { FabMenuVerticalFragment() },

        ScreenData("Charts", LIST_CHARTS) {
            ListFragment.createArguments(intArrayOf(
                CHART_TELEGRAM
            ))
        },
        ScreenData("Telegram chart", CHART_TELEGRAM) { ChartsFragment() },

        ScreenData("Transitions", LIST_TRANSITIONS) {
            ListFragment.createArguments(intArrayOf(
                TRANSITIONS_TOP_MENU
            ))
        },
        ScreenData("Top menu", TRANSITIONS_TOP_MENU) { TransitionsFragment() },

        ScreenData("Buttons", LIST_BUTTONS) { CategoryButtonsFragment() },
        ScreenData("Progressbar", LIST_PROGRESSBAR) { CategoryProgressBarFragment() },

        ScreenData("Particles", LIST_PARTICLES) {
            ListFragment.createArguments(intArrayOf(
                PARTICLES_LINKED,
                PARTICLES_SNOW,
                PARTICLES_RAIN
            ))
        },
        ScreenData("Linked", PARTICLES_LINKED) { LinkedParticlesFragment() },
        ScreenData("Snow", PARTICLES_SNOW) { SnowParticlesFragment() },
        ScreenData("Rain", PARTICLES_RAIN) { RainParticlesFragment() },

        ScreenData("Checkbox", LIST_CHECKBOX) { CategoryCheckboxFragment() },

        ScreenData("Gradients", LIST_GRADIENTS) {
            ListFragment.createArguments(intArrayOf(
                GRADIENT_ANIMATED
            ))
        },
        ScreenData("Animated", GRADIENT_ANIMATED) { GradientAnimatedFragment() }
    )

    fun fragmentFromId(key: Int) = fromKey(key).fragment()

    fun fromKey(key: Int): ScreenData = screens.first { it.fragmentId == key }
}