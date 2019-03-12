package au.sjowl.lib.view.app.transitions

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import au.sjowl.lib.uxlibrary.R
import au.sjowl.lib.view.app.BaseFragment
import au.sjowl.lib.view.recycler.BaseRecyclerViewAdapter
import au.sjowl.lib.view.recycler.BaseViewHolder
import kotlinx.android.synthetic.main.fr_transition_content.*
import kotlinx.android.synthetic.main.rv_item_content.view.*

class ContentFragment : BaseFragment() {

    override val layoutId: Int get() = R.layout.fr_transition_content

    private val adapter = ContentAdapter(object : ContentHolderListener {
        override fun onClick(data: DataItem) {
            println("click on ${data.title}")
        }
    }).apply {
        items = (0..100).map { DataItem("Item $it") }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}

data class DataItem(
    val title: String
)

interface ContentHolderListener {
    fun onClick(data: DataItem)
}

class ContentAdapter(
    private val onItemClickListener: ContentHolderListener
) : BaseRecyclerViewAdapter<DataItem, BaseViewHolder>() {

    override fun getViewHolderLayoutId(viewType: Int): Int = R.layout.rv_item_content

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ContentViewHolder(inflate(parent, viewType), onItemClickListener)
    }
}

class ContentViewHolder(
    view: View,
    private val listener: ContentHolderListener
) : BaseViewHolder(view) {

    override fun bind(item: Any) {
        (item as DataItem)
        with(itemView) {
            setOnClickListener { listener.onClick(item) }
            titleTextView.text = item.title
        }
    }
}