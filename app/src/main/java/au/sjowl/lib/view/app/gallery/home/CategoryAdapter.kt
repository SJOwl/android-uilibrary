package au.sjowl.lib.view.app.gallery.home

import android.view.ViewGroup
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.recycler.BaseRecyclerViewAdapter
import au.sjowl.lib.view.recycler.BaseViewHolder

class CategoryAdapter(
    private val onItemClickListener: CategoryHolderListener
) : BaseRecyclerViewAdapter<CategoryData, BaseViewHolder>() {

    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.rv_item_category

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CategoryViewHolder(inflate(parent, viewType), onItemClickListener)
    }
}