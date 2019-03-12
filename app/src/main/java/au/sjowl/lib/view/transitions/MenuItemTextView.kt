package au.sjowl.lib.view.transitions

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import org.jetbrains.anko.dip
import org.jetbrains.anko.sp
import org.jetbrains.anko.textColor

class MenuItemTextView : TextView {

    var menuItemId: Int = 0

    init {
        textSize = context.sp(10).toFloat()
        val h = context.dip(8)
        val v = context.dip(4)
        gravity = Gravity.LEFT
        setPadding(context.dip(40), v, h, v)
        textColor = Color.WHITE
    }

    fun init() {
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
}