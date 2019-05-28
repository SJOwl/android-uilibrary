package au.sjowl.lib.ui.views.seek

import android.content.Context
import android.graphics.Canvas

class SeekPointer(context: Context) {

    var onPositionChangedListener: (() -> Unit) = {}

    var currentX = 0f
        private set

    var position = 0

    var isDragging = false

    private var xStart = 0f

    private var xEnd = 0f

    private val dimens = RangeSeekDimensions(context)

    private val paints = RangeSeekPaints(context, dimens)

    private var width = 0f

    private var height = 0f

    private var start = 0f

    private var step = 0f

    private val currentPosition: Int get() = ((currentX - start) / step).toInt() + 1

    fun measure(width: Float, height: Float, start: Float, step: Float) {
        this.width = width
        this.height = height

        this.start = start
        this.step = step

        if (!isDragging) {
            currentX = start + step * (position - 1)
            xStart = currentX
            xEnd = currentX
        }
    }

    fun draw(canvas: Canvas, anim: Float) {
        currentX = xStart + (xEnd - xStart) * anim
        val shadowOffset = dimens.pointerRadius * 0.1f
        canvas.drawCircle(currentX + shadowOffset, height / 2 + shadowOffset, dimens.pointerRadius, paints.pointerShadow)
        canvas.drawCircle(currentX, height / 2, dimens.pointerRadius, paints.pointer)
    }

    fun isInBounds(x: Float) = x in currentX - dimens.pointerTouchRadius..currentX + dimens.pointerTouchRadius

    fun startDrag(x: Float) {
        if (isInBounds(x)) {
            isDragging = true
        }
        xEnd = currentX
        xStart = currentX
    }

    fun stopDrag() {
        if (isDragging) {
            xStart = currentX

            val positionPrev = position
            position = Math.round((currentX - start) / step + 1)
            if (positionPrev != position) onPositionChangedListener()

            xEnd = start + (position - 1) * step
        }
        isDragging = false
    }

    fun move(x: Float): Boolean {
        if (isDragging) {
            currentX = x
        }

        xEnd = currentX
        xStart = currentX

        val positionPrev = position
        position = currentPosition
        if (positionPrev != position) onPositionChangedListener()
        return isDragging
    }
}