package com.moondroid.behealthy.view.ui.item

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import com.google.gson.Gson
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.repeatOnStarted
import com.moondroid.behealthy.common.IntentParam
import com.moondroid.behealthy.common.ItemType
import com.moondroid.behealthy.common.TimeHelper
import com.moondroid.behealthy.databinding.FragmentItemListBinding
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.utils.BindingAdapter.visible
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment
import com.moondroid.behealthy.view.base.containType
import com.moondroid.behealthy.view.ui.item.ItemListViewModel.ItemListEvent
import com.moondroid.behealthy.view.ui.item.add.ItemAddActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class ItemListFragment : BaseFragment(R.layout.fragment_item_list) {
    private val binding by viewBinding(FragmentItemListBinding::bind)
    private val viewModel: ItemListViewModel by viewModels()

    private val bannerHandler = Handler(Looper.getMainLooper())
    private val itemHandler = Handler(Looper.getMainLooper())

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
        tempList?.let {
            binding.btnAdd.visible(!it.containType(ItemType.SMOKE, ItemType.DRINK))
        }

        binding.btnAdd.setOnClickListener {
            getResult.launch(Intent(mContext, ItemAddActivity::class.java).apply {
                putExtra(IntentParam.ITEMS, Gson().toJson(tempList))
            })
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
                    bannerHandler.postDelayed({ binding.banner.currentItem += 1 }, BANNER_DELAY)
                } else if (state == SCROLL_STATE_DRAGGING) {
                    bannerHandler.removeMessages(0)
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
        bannerHandler.postDelayed({ binding.banner.currentItem += 1 }, BANNER_DELAY)
        startReset()
    }

    override fun onPause() {
        super.onPause()
        bannerHandler.removeMessages(0)
        task?.let {
            it.cancel()
            task = null
        }
    }

    private var task: TimerTask? = null
    private fun startReset() {
        task = object : TimerTask() {
            override fun run() {
                tempList?.let {
                    ContextCompat.getMainExecutor(mContext).execute {
                        itemListAdapter.update(it)
                    }
                }
            }
        }

        val timer = Timer()
        timer.scheduleAtFixedRate(task, 0, TimeHelper.MINUTE)
    }

    private var tempList: List<Item>? = null

    private fun handleEvent(event: ItemListEvent) {
        when (event) {
            is ItemListEvent.UpdateItem -> {
                event.list.run {
                    binding.btnAdd.visible(!containType(ItemType.SMOKE, ItemType.DRINK))
                    tempList = this
                    itemListAdapter.update(this)
                }
            }

            is ItemListEvent.Saying -> bannerAdapter.update(event.urls)
        }
    }

    companion object {
        const val BANNER_DELAY = 5000L
    }
}