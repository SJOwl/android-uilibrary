package au.sjowl.lib.view.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import au.sjowl.lib.uxlibrary.R

class MockView : View {

    private val linesPaint = Paint().apply {
        isAntiAlias = true
        color = context.colorCompat(R.color.mockLines)
        strokeWidth = 4f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), linesPaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), 0f, linesPaint)
    }

//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        println("action mock dispatchTouchEvent ${event.action}")
//        return super.dispatchTouchEvent(event)
//    }
//
//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        println("action mock onTouchEvent ${event.action}")
//        return true
//    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}