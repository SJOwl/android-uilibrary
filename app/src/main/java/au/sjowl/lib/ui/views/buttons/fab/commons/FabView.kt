package au.sjowl.lib.ui.views.buttons.fab.commons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import au.sjowl.lib.ui.views.animations.ViewStateAnimator

class FabView : View {

    lateinit var animator: ViewStateAnimator

    lateinit var icon: Drawable

    private val colorBackground get() = animator.getColor(FmState.COLOR_FAB)

    private val colorTint: Int get() = animator.getColor(FmState.COLOR_FAB_ICON)

    private val circlePaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val p = paddingRight
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    outline.setOval(p, p, w - p, h - p)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        val cx = width / 2f
        val cy = height / 2f
        val r = cx - circlePaint.strokeWidth - paddingRight / 2
        circlePaint.color = colorBackground
        canvas.drawCircle(cx, cy, r, circlePaint)
        icon.setBounds((cx - r / 2).toInt(), (cy - r / 2).toInt(), (cx + r / 2).toInt(), (cy + r / 2).toInt())
        icon.setTint(colorTint)
        icon.draw(canvas)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}