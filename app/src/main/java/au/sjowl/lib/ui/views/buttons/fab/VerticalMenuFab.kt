package au.sjowl.lib.ui.views.buttons.fab

import android.content.Context
import android.util.AttributeSet
import au.sjowl.lib.ui.views.animations.ViewStateAnimator
import au.sjowl.lib.ui.views.animations.interpolators.providers.DefaultInterpolatorProvider
import au.sjowl.lib.ui.views.buttons.fab.commons.BaseMenuFab
import au.sjowl.lib.ui.views.buttons.fab.commons.FmState
import au.sjowl.lib.ui.views.utils.gone
import au.sjowl.lib.ui.views.utils.scale
import au.sjowl.lib.ui.views.utils.show
import au.sjowl.lib.uxlibrary.R
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.dimen

class VerticalMenuFab : BaseMenuFab {

    override val animator = ViewStateAnimator(180L, DefaultInterpolatorProvider())

    private val marginItems = context.dimen(R.dimen.fab_menu_vertical_margins)

    private val multiplier get() = animator.getFloat(FmState.MULT) // [0f;1f]

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (multiplier > 0f) {
            backgroundColor = animator.getColor(FmState.COLOR_BG)
        } else {
            backgroundDrawable = null
        }

        var r = baseWidth / 2
        val cx = right - r - paddingRight
        var cy = bottom - r - paddingBottom
        fab.layout(cx - r, cy - r, cx + r, cy + r)
        fab.postInvalidate()
        fab.rotation = 315 * multiplier
        fab.scale(-0.3f * multiplier + 1)

        r = baseWidth / 2 - paddingRight / 4
        for ((index, it) in items.withIndex()) {
            if (multiplier > 0f) {
                it.show()
                cy = bottom - paddingBottom - r - ((2 * (index + 1) * r + marginItems * (index + 1)) * multiplier).toInt()
                it.layout(cx - r, cy - r, cx + r, cy + r)
                it.rotation = (1f - multiplier) * 180
                it.scale((multiplier + 1f) / 2)
            } else {
                it.gone()
            }
        }
    }

    init {
        init()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}