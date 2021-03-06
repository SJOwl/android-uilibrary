package au.sjowl.lib.ui.views.buttons.fab.commons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import au.sjowl.lib.ui.views.animations.ViewStateAnimator
import au.sjowl.lib.ui.views.utils.colorCompat
import au.sjowl.lib.ui.views.utils.setTintCompat
import au.sjowl.lib.uxlibrary.R

class FabMenuView : View {

    lateinit var animator: ViewStateAnimator

    lateinit var icon: Drawable

    private var colorTint: Int = context.colorCompat(R.color.fab_tint)

    private val circlePaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 5f
        style = Paint.Style.STROKE
        color = colorTint
    }

    private val circleBgPaint = Paint().apply {
        isAntiAlias = true
        strokeWidth = 5f
        style = Paint.Style.FILL
        color = context.colorCompat(R.color.fab_menu_bg)
    }

    override fun onDraw(canvas: Canvas) {
        val cx = width / 2f
        val cy = height / 2f
        val r = cx - circlePaint.strokeWidth
        canvas.drawCircle(cx, cy, r, circlePaint)
        icon.setBounds((cx - r / 2).toInt(), (cy - r / 2).toInt(), (cx + r / 2).toInt(), (cy + r / 2).toInt())
        icon.setTintCompat(colorTint)
        icon.draw(canvas)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}