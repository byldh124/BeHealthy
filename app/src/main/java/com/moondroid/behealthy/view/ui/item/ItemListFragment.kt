package com.moondroid.behealthy.view.ui.item

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.databinding.FragmentItemListBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment

class ItemListFragment : BaseFragment(R.layout.fragment_item_list) {
    private val binding by viewBinding(FragmentItemListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(mContext, ItemAddActivity::class.java))
        }
    }

}