package com.moondroid.behealthy.view.ui.item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_SETTLING
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.repeatOnStarted
import com.moondroid.behealthy.common.IntentParam
import com.moondroid.behealthy.databinding.FragmentItemListBinding
import com.moondroid.behealthy.utils.BindingAdapter.visible
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment
import com.moondroid.behealthy.view.ui.item.ItemListViewModel.ItemListEvent
import com.moondroid.behealthy.view.ui.item.add.ItemAddActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : BaseFragment(R.layout.fragment_item_list) {
    private val binding by viewBinding(FragmentItemListBinding::bind)
    private val viewModel: ItemListViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())

    private val bannerAdapter = ItemBannerAdapter()

    private val itemListAdapter = ItemListAdapter {

    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) viewModel.getItems()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getItems()
        initView()
    }


    private fun initView() {
        binding.btnAdd.setOnClickListener {
            getResult.launch(Intent(mContext, ItemAddActivity::class.java))
        }

        binding.banner.adapter = bannerAdapter
        binding.banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                /**
                 * The user swiped forward or back and we need to
                 * invalidate the previous handler.
                 */
                if (state == SCROLL_STATE_IDLE) {
                    handler.postDelayed({ binding.banner.currentItem += 1 }, BANNER_DELAY)
                } else if (state == SCROLL_STATE_DRAGGING) {
                    handler.removeMessages(0)
                }
            }
        })

        binding.recycler.run {
            layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
            adapter = itemListAdapter
        }

    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed({ binding.banner.currentItem += 1 }, BANNER_DELAY)
    }

    override fun onPause() {
        super.onPause()
        handler.removeMessages(0)
    }

    private fun handleEvent(event: ItemListEvent) {
        when (event) {
            is ItemListEvent.UpdateItem -> itemListAdapter.update(event.list)
            is ItemListEvent.Saying -> bannerAdapter.update(event.urls)
        }
    }

    companion object {
        const val BANNER_DELAY = 5000L
    }
}