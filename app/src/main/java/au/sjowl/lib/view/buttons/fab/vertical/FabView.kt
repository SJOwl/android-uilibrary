package au.sjowl.lib.view.buttons.fab.vertical

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import au.sjowl.lib.view.animations.ViewStateAnimator
import au.sjowl.lib.view.utils.measureDrawingMs

class FabView : View {

    lateinit var animator: ViewStateAnimator

    lateinit var icon: Drawable

    private val colorBackground get() = animator.getColor(FmState.COLOR_FAB)

    private val colorTint: Int get() = animator.getColor(FmState.COLOR_FAB_ICON)

    private val circlePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        measureDrawingMs("fab") {
            val cx = width / 2f
            val cy = height / 2f
            val r = cx - circlePaint.strokeWidth
            circlePaint.color = colorBackground
//            canvas.drawCircle(cx, cy, r, circlePaint)
            icon.setBounds((cx - r / 2).toInt(), (cy - r / 2).toInt(), (cx + r / 2).toInt(), (cy + r / 2).toInt())
            icon.setTint(colorTint)
            icon.draw(canvas)
        }
    }

    // todo animate elevation on click

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}