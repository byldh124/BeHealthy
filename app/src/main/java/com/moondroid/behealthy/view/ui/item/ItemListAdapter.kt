package com.moondroid.behealthy.view.ui.item

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.databinding.ItemItemListBinding
import com.moondroid.behealthy.domain.model.Item
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class ItemListAdapter(private val onClick: (Item) -> Unit) : Adapter<ItemListAdapter.ItemListViewHolder>() {

    private var items: List<Item> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun update(newList: List<Item>) {
        items = newList
    }

    inner class ItemListViewHolder(
        private val binding: ItemItemListBinding,
    ) : ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.item = item
            binding.root.setOnClickListener {
                onClick(item)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return ItemListViewHolder(
            ItemItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bind(items[position])
    }
}