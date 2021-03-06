package au.sjowl.lib.ui.app.gallery.home

import android.view.ViewGroup
import au.sjowl.lib.ui.views.recycler.BaseRecyclerViewAdapter
import au.sjowl.lib.ui.views.recycler.BaseViewHolder
import au.sjowl.lib.uxlibrary.R

class CategoryAdapter(
    private val onItemClickListener: CategoryHolderListener
) : BaseRecyclerViewAdapter<ScreenData, BaseViewHolder>() {

    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.rv_item_category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CategoryViewHolder(inflate(parent, viewType), onItemClickListener)
    }
}