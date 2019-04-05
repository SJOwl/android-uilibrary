package au.sjowl.lib.ui.views.particles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorInt
import kotlin.random.Random

class RainParticleView : BaseInfiniteAnimationSurfaceView {

    var background = Color.BLACK

    var bgDrawable: Drawable? = null

    var particlesParams = ParticlesParams()

    private lateinit var particles: Array<Particle>

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 2F
        color = Color.WHITE
    }

    private var t = System.currentTimeMillis()

    override fun drawSurface(canvas: Canvas) {
        printTime()
        canvas.drawColor(background)
        bgDrawable?.run {
            setBounds(0, 0, measuredWidth, measuredHeight)
            draw(canvas)
        }
        drawParticles(canvas)
    }

    fun applyChanges() {
        post {
            paint.color = particlesParams.color
            particles = Array(particlesParams.count) {
                val v = Random.nextInt(particlesParams.velocityMin, particlesParams.velocityMax).toFloat()
                Particle(
                    radius = 1f,
                    x = Random.nextInt(0, width).toFloat(),
                    y = Random.nextInt(0, height).toFloat(),
                    vx = v * particlesParams.cos,
                    vy = v * particlesParams.sin,
                    alpha = (v / particlesParams.velocityMax * 100).toInt()
                )
            }
            restart()
        }
    }

    private fun printTime() {
        println("t = ${System.currentTimeMillis() - t}")
        t = System.currentTimeMillis()
    }

    private fun drawParticles(canvas: Canvas) {
        var p: Particle

        paint.strokeWidth = particlesParams.width
        for (i in 0 until particlesParams.count) {
            p = particles[i]
            p.x += p.vx
            p.y += p.vy

            if (p.x < 0)
                p.vx = width.toFloat()
            else if (p.x > width)
                p.x = 0F

            if (p.y < 0)
                p.y = height.toFloat()
            else if (p.y > height)
                p.y = 0F

            if (p.x < 0 || p.x > width) p.vx = -p.vx
            if (p.y < 0 || p.y > height) p.vy = -p.vy

            paint.alpha = p.alpha
            canvas.drawLine(p.x, p.y, p.x - p.vx, p.y - p.vy, paint)
        }
    }

    private fun init() {
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    open class ParticlesParams(
        val velocityMin: Int = 20,
        val velocityMax: Int = 40,
        angleDegrees: Int = 70,
        val width: Float = 7f,
        @ColorInt var color: Int = 0,
        var count: Int = 10
    ) {
        val angleRad = (angleDegrees * Math.PI / 180)
        val cos = Math.cos(angleRad).toFloat()
        val sin = Math.sin(angleRad).toFloat()
    }
}
