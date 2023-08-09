package com.moondroid.behealthy.view.ui.item.add

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.ItemType
import com.moondroid.behealthy.databinding.FragmentItemSelectBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment

class ItemSelectFragment : BaseFragment(R.layout.fragment_item_select) {
    private val binding by viewBinding(FragmentItemSelectBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            icBack.setOnClickListener {
                (mContext as ItemAddActivity).finish()
            }
            smkWrapper.setOnClickListener {
                findNavController().navigate(ItemSelectFragmentDirections.toItemSettingFragment(ItemType.SMOKE))
            }

            drkWrapper.setOnClickListener {
                findNavController().navigate(ItemSelectFragmentDirections.toItemSettingFragment(ItemType.DRINK))
            }
        }
    }
}