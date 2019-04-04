package au.sjowl.lib.ui.views.particles

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

abstract class BaseSurfaceView : SurfaceView, SurfaceHolder.Callback {

    private var surfaceViewThread: SurfaceViewThread? = null

    private var hasSurface: Boolean = false

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        hasSurface = true

        if (surfaceViewThread == null)
            surfaceViewThread = SurfaceViewThread()

        surfaceViewThread!!.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        hasSurface = false
        surfaceViewThread?.requestExitAndWait()
        surfaceViewThread = null
    }

    /**
     * Call from onPause() of host Fragment/Activity
     */
    fun stopThread() {
        surfaceViewThread?.requestExitAndWait()
        surfaceViewThread = null
    }

    protected fun restart() {
        surfaceViewThread?.requestExitAndWait()
        surfaceViewThread = SurfaceViewThread()
        hasSurface = true
        surfaceViewThread!!.start()
    }

    private fun init() {
        if (holder != null)
            holder.addCallback(this)

        hasSurface = false
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private inner class SurfaceViewThread : Thread() {

        private var running: Boolean = true
        private var t = 0L

        override fun run() {
            while (running) {
                if (System.currentTimeMillis() - t < 16) continue

                var canvas: Canvas? = null

                try {
                    canvas = holder.lockCanvas()

                    synchronized(holder) {
                        t = System.currentTimeMillis()
                        draw(canvas)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas)
                    }
                }
            }
        }

        fun requestExitAndWait() {
            running = false

            try {
                join()
            } catch (ignored: InterruptedException) {
            }
        }
    }
}