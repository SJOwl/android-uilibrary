package au.sjowl.lib.ui.app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import au.sjowl.lib.ui.views.bottomnav.Boundaries
import au.sjowl.lib.ui.views.utils.measureDrawingMs

class ShadersFragment : BaseFragment() {
    override val layoutId: Int get() = au.sjowl.lib.uxlibrary.R.layout.fr_shaders

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

class ShaderTestView : View {

    private var shaderBg = LinearGradient(0f, 0f, 1f, 1f, Color.GREEN, Color.BLUE, Shader.TileMode.CLAMP)

    private val paintBg = Paint().apply {
        isAntiAlias = true
//        colorFilter = ColorMatrixColorFilter(getColorMatrix())
        setShadowLayer(20f, 10f, 10f, Color.BLACK)
    }

    private val boundaries = Boundaries()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        shaderBg = LinearGradient(0f, 0f, w * 1f, h * 1f, Color.GREEN, Color.BLUE, Shader.TileMode.CLAMP)
        paintBg.shader = shaderBg
        boundaries.centerX = w / 2f
        boundaries.centerY = h / 2f
        boundaries.radius = w / 3f
    }

    override fun onDraw(canvas: Canvas) {
        measureDrawingMs("shaderBg") {
            canvas.drawRect(boundaries.rect, paintBg)
        }
    }

    private fun getColorMatrix(): ColorMatrix {
        val colorMatrix = ColorMatrix(floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f
        ))
        return colorMatrix
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}