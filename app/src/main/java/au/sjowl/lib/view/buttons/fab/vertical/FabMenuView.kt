package au.sjowl.lib.view.buttons.fab.vertical

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import au.sjowl.lib.view.animations.ViewStateAnimator
import au.sjowl.lib.view.utils.measureDrawingMs

class FabMenuView : View {

    var data: FabMenuItem? = null
        set(value) {
            field = value
            value?.let { value ->
                icon = ContextCompat.getDrawable(context, value.iconDrawableId) as Drawable
                colorTint = value.colorTint
            }
        }

    lateinit var animator: ViewStateAnimator

    private lateinit var icon: Drawable

    private var colorTint: Int = Color.RED
        set(value) {
            field = value
            circlePaint.color = value
        }

    private val circlePaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 5f
        style = Paint.Style.STROKE
        color = colorTint
    }

    override fun onDraw(canvas: Canvas) {
        measureDrawingMs("menu") {
            val cx = width / 2f
            val cy = height / 2f
            val r = cx - circlePaint.strokeWidth
            canvas.drawCircle(cx, cy, r, circlePaint)
            icon.setBounds((cx - r / 2).toInt(), (cy - r / 2).toInt(), (cx + r / 2).toInt(), (cy + r / 2).toInt())
            icon.setTint(colorTint)
            icon.draw(canvas)
        }
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}