package com.moondroid.legacy.view.ui.item.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.moondroid.legacy.databinding.ItemBannerBinding
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class ItemBannerAdapter() : Adapter<ItemBannerAdapter.ItemBannerViewHolder>() {

    private var urls: List<String> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun update(newList: List<String>) {
        urls = newList
    }

    inner class ItemBannerViewHolder(
        private val binding: ItemBannerBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            Glide.with(binding.root.context).load(url).into(binding.imgView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemBannerViewHolder {
        return ItemBannerViewHolder(
            ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = Int.MAX_VALUE

    override fun onBindViewHolder(holder: ItemBannerViewHolder, position: Int) {
        if (urls.isNotEmpty()) {
            holder.bind(urls[position % urls.size])
        }
    }
}