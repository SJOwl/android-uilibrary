package au.sjowl.lib.view.buttons.fab

import android.content.Context
import android.util.AttributeSet
import au.sjowl.lib.view.buttons.fab.commons.BaseMenuFab
import au.sjowl.lib.view.buttons.fab.commons.FmState
import au.sjowl.lib.view.utils.gone
import au.sjowl.lib.view.utils.scale
import au.sjowl.lib.view.utils.show

class CircularMenuFab : BaseMenuFab {

    private val multiplier get() = animator.getFloat(FmState.MULT) // [0f;1f]

    private val radiusMax = (2 * Math.sqrt(2.0)).toFloat() * baseWidth

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val r = baseWidth / 2
        val cx = right - r - paddingRight
        var cy = bottom - r - paddingBottom
        fab.layout(cx - r, cy - r, cx + r, cy + r)
        fab.postInvalidate()
        fab.rotation = 315 * multiplier
        fab.scale(-0.3f * multiplier + 1)

        val discreteAngle = 90f / (items.size - 1) * Math.PI / 180
        for ((index, it) in items.withIndex()) {
            if (multiplier > 0f) {
                it.show()
                val fi = index * discreteAngle * multiplier
                val centerR = radiusMax * multiplier / 2
                val icx = (cx - centerR * Math.sin(fi)).toInt()
                val icy = (cy - centerR * Math.cos(fi)).toInt()
                it.layout(icx - r, icy - r, icx + r, icy + r)
                it.scale((multiplier + 1f) / 2)
            } else {
                it.gone()
            }
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}