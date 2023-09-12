package com.moondroid.legacy.view.ui.item.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import com.moondroid.legacy.common.Extensions.debug
import com.moondroid.legacy.common.Extensions.logException
import com.moondroid.legacy.common.Extensions.repeatOnStarted
import com.moondroid.legacy.common.ItemType
import com.moondroid.legacy.common.TimeHelper
import com.moondroid.legacy.domain.model.Item
import com.moondroid.legacy.domain.model.status.onError
import com.moondroid.legacy.domain.model.status.onFail
import com.moondroid.legacy.domain.model.status.onSuccess
import com.moondroid.legacy.domain.usecase.item.ChangeBoxColorUseCase
import com.moondroid.legacy.R
import com.moondroid.legacy.databinding.FragmentItemListBinding
import com.moondroid.legacy.utils.BindingAdapter.visible
import com.moondroid.legacy.utils.viewBinding
import com.moondroid.legacy.view.base.BaseFragment
import com.moondroid.legacy.view.base.containType
import com.moondroid.legacy.view.ui.home.HomeActivity
import com.moondroid.legacy.view.ui.item.list.ItemListViewModel.ItemListEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class ItemListFragment : BaseFragment(R.layout.fragment_item_list) {
    private val binding by viewBinding(FragmentItemListBinding::bind)
    private val viewModel: ItemListViewModel by viewModels()

    private val bannerHandler = Handler(Looper.getMainLooper())
    private val bannerAdapter = ItemBannerAdapter()

    @Inject
    lateinit var changeBoxColorUseCase: ChangeBoxColorUseCase

    private val itemListAdapter = ItemListAdapter {
        CoroutineScope(Dispatchers.Main).launch {
            changeBoxColorUseCase(it.index, Random.nextInt(29) + 1).collect { result ->
                result.onSuccess {
                    debug("onSuccess")
                    viewModel.getItems()
                }.onError {
                    it.logException()
                }.onFail {
                    debug("code")
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debug("onCreate()")
        repeatOnStarted {
            viewModel.eventFlow.collect { handleEvent(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debug("onViewCreated()")
        viewModel.getItems()
        initView()
    }


    private fun initView() {
        tempList?.let {
            binding.btnAdd.visible(!it.containType(ItemType.SMOKE, ItemType.DRINK))
        }

        binding.btnAdd.setOnClickListener {
            (mContext as HomeActivity).toAddItem(tempList) {
                debug("toAddItems Callback")
                viewModel.getItems()
            }
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

    override fun onDestroy() {
        super.onDestroy()
        debug("onDestroy()")
    }

    companion object {
        const val BANNER_DELAY = 5000L
    }
}