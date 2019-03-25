package au.sjowl.lib.ui.app.test.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

fun printEvent(v: String, method: String, returned: Boolean, event: MotionEvent) {
    println("action %10s %-10s %-6s %-10s %-10b".format("", eventToAction(event), v, method, returned))
}

fun eventToAction(event: MotionEvent): String {
    return when (event.action) {
        MotionEvent.ACTION_DOWN -> "down"
        MotionEvent.ACTION_MOVE -> "move"
        MotionEvent.ACTION_UP -> "up"
        MotionEvent.ACTION_CANCEL -> "cancel"
        MotionEvent.ACTION_OUTSIDE -> "outside"
        MotionEvent.ACTION_SCROLL -> "scroll"
        else -> event.action.toString()
    }
}

class TouchRoot : ConstraintLayout {

    var rDispatch = false
    var rIntercept = false
    var rTouch = false

//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        pev("dispatch", rDispatch, event)
//        return rDispatch
//    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        rIntercept = when (event.action) {
            MotionEvent.ACTION_DOWN -> false
            MotionEvent.ACTION_MOVE -> true
            MotionEvent.ACTION_UP -> false
            else -> false
        }
        pev("intercept", rIntercept, event)

        return rIntercept
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        pev("touch", rTouch, event)
        return rTouch
    }

    fun pev(method: String, returned: Boolean, event: MotionEvent) = printEvent("root", method, returned, event)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}

class TouchViewGroup : ConstraintLayout {

    var rDispatch = false
    var rIntercept = false
    var rTouch = false

//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        pev("dispatch", rDispatch, event)
//        return rDispatch
//    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        pev("intercept", rIntercept, event)
        return rIntercept
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        pev("touch", rTouch, event)
        return rTouch
    }

    fun pev(method: String, returned: Boolean, event: MotionEvent) = printEvent("group", method, returned, event)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}

class TouchView : View {
    var rDispatch = false
    var rTouch = false

//    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
//        pev("dispatch", rDispatch, event)
//        return rDispatch
//    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        pev("touch", rTouch, event)
        return rTouch
    }

    fun pev(method: String, returned: Boolean, event: MotionEvent) = printEvent("view", method, returned, event)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}