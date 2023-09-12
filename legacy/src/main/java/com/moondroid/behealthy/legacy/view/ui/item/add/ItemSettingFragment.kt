package com.moondroid.behealthy.legacy.view.ui.item.add

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.common.ItemType
import com.moondroid.behealthy.domain.model.status.onError
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.item.AddItemUseCase
import com.moondroid.behealthy.legacy.BHApp
import com.moondroid.behealthy.legacy.R
import com.moondroid.behealthy.legacy.databinding.FragmentItemSettingBinding
import com.moondroid.behealthy.legacy.databinding.LayoutItemAddDrkBinding
import com.moondroid.behealthy.legacy.databinding.LayoutItemAddSmkBinding
import com.moondroid.behealthy.legacy.utils.viewBinding
import com.moondroid.behealthy.legacy.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class ItemSettingFragment : BaseFragment(R.layout.fragment_item_setting) {
    private val binding by viewBinding(FragmentItemSettingBinding::bind)

    private var _smkBinding: LayoutItemAddSmkBinding? = null
    private val smkBinding get() = _smkBinding!!

    private var _drkBinding: LayoutItemAddDrkBinding? = null
    private val drkBinding get() = _drkBinding!!

    private val args by navArgs<ItemSettingFragmentArgs>()
    private val itemType by lazy { args.itemType }

    @Inject
    lateinit var addItemUseCase: AddItemUseCase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.itemType = itemType

        _smkBinding = LayoutItemAddSmkBinding.bind(binding.smkLayout.root)
        _drkBinding = LayoutItemAddDrkBinding.bind(binding.drkLayout.root)

        initView()
    }

    private fun initView() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnDone.setOnClickListener {
            addItem()
        }
    }

    private fun addItem() {
        val amount: Float = when (itemType) {
            ItemType.SMOKE -> getSmkAmount()
            ItemType.DRINK -> getDrkAmount()
            else -> throw IllegalStateException()
        }

        val cost: Long = when (itemType) {
            ItemType.SMOKE -> 4500
            ItemType.DRINK -> getDrkCost()
            else -> throw IllegalStateException()
        }

        CoroutineScope(Dispatchers.Main).launch {
            val boxColor = (mContext as ItemAddActivity).run {
                if (list.isEmpty()) Random.nextInt(29) + 1
                else (list[list.lastIndex].boxColor + 1) % 29 + 1
            }
            addItemUseCase
                .addItem(BHApp.profile.id, itemType, System.currentTimeMillis(), amount, cost, boxColor)
                .collect { result ->
                    result.onSuccess {
                        (mContext as ItemAddActivity).run {
                            setResult(Activity.RESULT_OK)
                            showMessage("새로운 아이템이 추가됐습니다.", ::finish)
                        }
                    }.onError {
                        it.logException()
                    }
                }
        }
    }

    private fun getSmkAmount(): Float {
        val position = smkBinding.amountSpinner.selectedItemPosition
        return if (position == 0) 0.5f else position.toFloat()
    }

    private fun getDrkAmount(): Float {
        val position = drkBinding.amountSpinner.selectedItemPosition
        return (position + 1).toFloat()
    }

    private fun getDrkCost(): Long {
        val position = drkBinding.costSpinner.selectedItemPosition
        return (position + 1) * 10000L
    }

    override fun onDestroy() {
        super.onDestroy()
        _smkBinding = null
        _drkBinding = null
    }
}