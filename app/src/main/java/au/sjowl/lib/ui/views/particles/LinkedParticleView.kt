package au.sjowl.lib.ui.views.particles

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import kotlin.random.Random

class LinkedParticleView : BaseInfiniteAnimationSurfaceView {

    var background = Color.BLACK

    var particlesParams = ParticlesParams()

    var isTouching = false

    private lateinit var particles: Array<Particle>

    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2F
        color = Color.WHITE
    }

    private var touchX = 0f

    private var touchY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        println("touching ${event.x}, ${event.y}")
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouching = true
                particles[particlesParams.count - 1].x = event.x
                particles[particlesParams.count - 1].y = event.y
                particles[particlesParams.count - 1].vx = 0f
                particles[particlesParams.count - 1].vy = 0f

                touchX = event.x
                touchY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                isTouching = true
                particles[particlesParams.count - 1].x = event.x
                particles[particlesParams.count - 1].y = event.y
                touchX = event.x
                touchY = event.y
            }
            MotionEvent.ACTION_UP -> {
                isTouching = false
                for (i in 0 until particlesParams.count - 1) {
                    val p = particles[i]
                    p.vx = Random.nextInt(particlesParams.velocityXMin, particlesParams.velocityXMax).toFloat()
                    p.vy = Random.nextInt(particlesParams.velocityYMin, particlesParams.velocityYMax).toFloat()
                }
            }
        }
        return true
    }

    override fun drawSurface(canvas: Canvas) {
        super.draw(canvas)
            canvas.drawColor(background)
            drawParticles(canvas)
            drawLinks(canvas)
    }

    fun applyChanges() {
        post {
            paint.color = particlesParams.color

            particles = Array(particlesParams.count) {
                Particle(
                    radius = Random.nextInt(particlesParams.radiusMin, particlesParams.radiusMax).toFloat(),
                    x = Random.nextInt(0, measuredWidth).toFloat(),
                    y = Random.nextInt(0, measuredHeight).toFloat(),
                    vx = Random.nextInt(particlesParams.velocityXMin, particlesParams.velocityXMax).toFloat(),
                    vy = Random.nextInt(particlesParams.velocityYMin, particlesParams.velocityYMax).toFloat(),
                    alpha = Random.nextInt(150, 255)
                )
            }
            restart()
        }
    }

    private fun drawParticles(canvas: Canvas) {
        var p: Particle

        val kx = 1f * particlesParams.velocityXMax / measuredWidth
        val ky = 1f * particlesParams.velocityYMax / measuredHeight
        for (i in 0 until particlesParams.count - 1) {
            p = particles[i]

            if (isTouching) {
                val x = (touchX - p.x)
                val y = (touchY - p.y)
                p.vx += kx * x
                p.vy += ky * y
            }

            p.x += p.vx
            p.y += p.vy

            if (p.x < 0 || p.x > measuredWidth) p.vx = -p.vx
            if (p.y < 0 || p.y > measuredHeight) p.vy = -p.vy

            paint.alpha = p.alpha
            canvas.drawCircle(p.x, p.y, p.radius, paint)
        }
    }

    private fun drawLinks(canvas: Canvas) {
        val div = 10
        val r = width / div * width / div + height / div * height / div
        for (i in 0 until if (isTouching) particlesParams.count else particlesParams.count - 1) {
            for (j in 0 until if (isTouching) particlesParams.count else particlesParams.count - 1) {
                val p1 = particles[i]
                val p2 = particles[j]
                val dx = p1.x - p2.x
                val dy = p1.y - p2.y
                val dist = (dx * dx + dy * dy).toInt()

                if (dist < r) {
                    paint.alpha = 255 - (255f * dist / r).toInt()
                    canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint)
                }
            }
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
        val color: Int = 0,
        val count: Int = 50,
        val radiusMin: Int = 2,
        val radiusMax: Int = 10,
        velocityX: Int = 7,
        velocityY: Int = 7
    ) {
        val velocityXMin: Int = -velocityX
        val velocityXMax: Int = velocityX
        val velocityYMin: Int = -velocityY
        val velocityYMax: Int = velocityY
    }
}