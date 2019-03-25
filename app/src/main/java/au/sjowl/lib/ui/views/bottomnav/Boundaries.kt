package au.sjowl.lib.ui.views.bottomnav

import android.graphics.Rect
import android.graphics.RectF

class Boundaries(
    var centerX: Float = 0f,
    var centerY: Float = 0f,
    var radius: Float = 0f
) {

    val rect: Rect
        get() {
            r.left = (centerX - radius).toInt()
            r.right = (centerX + radius).toInt()
            r.top = (centerY - radius).toInt()
            r.bottom = (centerY + radius).toInt()
            return r
        }

    val rectf: RectF
        get() {
            rf.left = (centerX - radius).toFloat()
            rf.right = (centerX + radius).toFloat()
            rf.top = (centerY - radius).toFloat()
            rf.bottom = (centerY + radius).toFloat()
            return rf
        }

    val left get() = (centerX - radius).toInt()

    val right get() = (centerX + radius).toInt()

    val top get() = (centerY - radius).toInt()

    val bottom get() = (centerY + radius).toInt()

    val leftF get() = centerX - radius
    val rightF get() = centerX + radius
    val topF get() = centerY - radius
    val bottomF get() = centerY + radius

    private val r = Rect()

    private val rf = RectF()
}