package au.sjowl.lib.view.app.gallery.home

import android.view.View
import au.sjowl.lib.view.recycler.BaseViewHolder
import kotlinx.android.synthetic.main.rv_item_category.view.*

class CategoryViewHolder(
    view: View,
    private val listener: CategoryHolderListener
) : BaseViewHolder(view) {

    override fun bind(item: Any) {
        (item as CategoryData)
        with(itemView) {
            setOnClickListener { listener.onClick(item) }
            autoscrollTextView.text = item.name
        }
    }
}