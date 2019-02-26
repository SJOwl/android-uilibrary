package au.sjowl.lib.view.bottomnav

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import au.sjowl.lib.view.utils.absCos
import au.sjowl.lib.view.utils.absSin
import au.sjowl.lib.view.utils.drawTextCentered
import org.jetbrains.anko.dip

class Badge(
    private var context: Context,
    count: Int = 0
) {

    var toAnimate: Boolean = true

    var count: Int = count
        set(value) {
            toAnimate = field == value
            field = value
        }

    val paintBadgeText = defaultPaint().apply {
        color = Color.WHITE
        typeface = Typeface.DEFAULT_BOLD
        textSize = context.dip(10).toFloat()
    }

    val paintBadge = defaultPaint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val drawRect = Rect()

    private var bitmap: Bitmap? = null

    private val amplitude = 0.4f

    private val rectSrc = Rect()

    private val rectDst = Rect()

    private val paddingRight = context.dip(4)

    override fun toString(): String {
        return when (count) {
            0 -> ""
            in 1..99 -> count.toString()
            in 100..999 -> "99+"
            in 1000..999_999 -> "${count / 1000}k"
            in 1_000_000..9_999_999 -> "${count / 1_000_000}M"
            else -> count.toString()
        }
    }

    fun draw(canvas: Canvas, cx: Float, cy: Float, animFloat: Float, scale: Float) {

        val badgeText = toString()
        paintBadgeText.getTextBounds(badgeText, 0, badgeText.length, drawRect)

        var w = drawRect.width() * 1f
        var h = drawRect.height() * 1f
        if (w < h) w = h
        val r = h

        if (h != 0f && w != 0f) {
            val bitmapW = w.toInt() * 2
            val bitmapH = h.toInt() * 2

            if (bitmap == null || bitmap!!.width != bitmapW || bitmap!!.height != bitmapH) {
                bitmap = Bitmap.createBitmap(bitmapW, bitmapH, Bitmap.Config.ARGB_4444)
                Canvas(bitmap!!).apply {
                    drawRoundRect(0f, 0f, bitmapW.toFloat(), bitmapH.toFloat(), r, r, paintBadge)
                    drawTextCentered(badgeText, bitmapW / 2f, bitmapH / 2f, paintBadgeText, drawRect)
                }
            }

            val bmp = bitmap!!
            val scaleMultiplier = amplitude * absSin(scale * Math.PI) + 1f
            w *= absCos(animFloat * Math.PI) * scaleMultiplier
            h *= scaleMultiplier
            with(rectSrc) {
                right = bmp.width
                bottom = bmp.height
            }
            with(rectDst) {
                left = (cx - w).toInt()
                top = (cy - h).toInt()
                right = (cx + w).toInt()
                bottom = (cy + h).toInt()

                if (right > canvas.width) {
                    val delta = right - canvas.width + paddingRight
                    left -= delta
                    right -= delta
                }
            }
            canvas.drawBitmap(bmp, rectSrc, rectDst, null)
        }
    }

    private fun defaultPaint() = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        strokeWidth = 10f
        isAntiAlias = true
    }
}