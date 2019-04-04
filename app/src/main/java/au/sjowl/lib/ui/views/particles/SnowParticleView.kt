package au.sjowl.lib.ui.views.particles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import au.sjowl.lib.ui.views.utils.measureDrawingMs
import kotlin.random.Random

class SnowParticleView : BaseSurfaceView {

    var count = 10

    var background = Color.BLACK

    var color = 0

    var particlesParams = ParticlesParams()

    private lateinit var particles: Array<Particle>

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2F
        color = Color.WHITE
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        measureDrawingMs("points $count") {
            canvas.drawColor(background)
            drawParticles(canvas)
        }
    }

    fun applyChanges() {
        post {
            paint.color = color
            particles = Array(count) {
                Particle(
                    radius = Random.nextInt(particlesParams.radiusMin, particlesParams.radiusMax).toFloat(),
                    x = Random.nextInt(0, width).toFloat(),
                    y = Random.nextInt(0, height).toFloat(),
                    vx = Random.nextInt(particlesParams.velocityXMin, particlesParams.velocityXMax).toFloat(),
                    vy = Random.nextInt(particlesParams.velocityYMin, particlesParams.velocityYMax).toFloat(),
                    alpha = Random.nextInt(150, 255)
                )
            }
            restart()
        }
    }

    private fun init() {
    }

    private fun drawParticles(canvas: Canvas) {
        var p: Particle

        for (i in 0 until count) {
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
            canvas.drawCircle(p.x, p.y, p.radius, paint)
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    open class ParticlesParams(
        val radiusMin: Int = 2,
        val radiusMax: Int = 10,
        val velocityXMin: Int = -7,
        val velocityXMax: Int = 7,
        val velocityYMin: Int = -10,
        val velocityYMax: Int = 10
    )
}
