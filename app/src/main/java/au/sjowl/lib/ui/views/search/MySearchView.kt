package au.sjowl.lib.ui.views.search

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import au.sjowl.lib.ui.views.seek.vibrate
import au.sjowl.lib.ui.views.utils.constrain
import au.sjowl.lib.ui.views.utils.hide
import au.sjowl.lib.ui.views.utils.scale
import au.sjowl.lib.ui.views.utils.show
import au.sjowl.lib.uxlibrary.R
import kotlinx.android.synthetic.main.view_search.view.*
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onFocusChange

class MySearchView : ConstraintLayout {

    private val cs = ConstraintSet()

    private fun init() {
        context.layoutInflater.inflate(R.layout.view_search, this)

        closeImageView.hide()
        closeImageView.scale(0f)

        closeImageView.onClick {
            searchEditText.text?.clear()
            vibrate(20)
        }
        searchEditText.onFocusChange { v, hasFocus ->
            constrain(cs) {
                if (hasFocus) {
                    closeImageView.show()
                    closeImageView.scale(1f)
                } else {
                    closeImageView.hide()
                    closeImageView.scale(0f)
                }
            }
        }
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