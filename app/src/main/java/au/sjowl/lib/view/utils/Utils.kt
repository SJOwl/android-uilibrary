package au.sjowl.lib.view.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.content.ContextCompat

inline fun absCos(value: Float) = Math.abs(Math.cos(value * 1.0)).toFloat()
inline fun absCos(value: Double) = Math.abs(Math.cos(value)).toFloat()

inline fun absSin(value: Float) = Math.abs(Math.sin(value * 1.0)).toFloat()
inline fun absSin(value: Double) = Math.abs(Math.sin(value)).toFloat()

fun Canvas.drawTextCenteredVertically(text: String, x: Float, y: Float, paint: Paint, r: Rect) {
    paint.getTextBounds(text, 0, text.length, r)
    drawText(text, x, y + r.height() / 2, paint)
}

fun Canvas.drawTextCentered(text: String, x: Float, y: Float, paint: Paint, r: Rect) {
    paint.getTextBounds(text, 0, text.length, r)
    drawText(text, x - r.width() / 2, y + r.height() / 2, paint)
}

interface AnimProperty {
    fun setup()
    fun reverse()
}

class AnimatedPropertyF(
    var value: Float = 0f,
    var from: Float = 0f,
    var to: Float = 0f
) : AnimProperty {
    override fun setup() {
        value = from
    }

    override fun reverse() {
        val t = from
        from = to
        to = t
    }
}

class AnimatedPropertyInt(
    var value: Int = 0,
    var from: Int = 0,
    var to: Int = 0
) : AnimProperty {
    override fun setup() {
        value = from
    }

    override fun reverse() {
        val t = from
        from = to
        to = t
    }

    fun set(from: Int, to: Int, value: Int) {
        this.from = from
        this.to = to
        this.value = value
    }
}

fun Context.colorCompat(id: Int) = ContextCompat.getColor(this, id)
