package au.sjowl.lib.ui.views.recycler

import android.view.View

abstract class BaseEvenOddViewHolder(view: View) : BaseViewHolder(view) {
    abstract fun bindEven(item: Any)
    abstract fun bindOdd(item: Any)
}