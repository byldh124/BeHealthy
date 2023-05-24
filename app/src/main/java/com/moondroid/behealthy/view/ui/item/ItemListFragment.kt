package com.moondroid.behealthy.view.ui.item

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moondroid.behealthy.R
import com.moondroid.behealthy.databinding.FragmentItemListBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment
import com.moondroid.behealthy.view.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemListFragment : BaseFragment(R.layout.fragment_item_list) {
    private val binding by viewBinding(FragmentItemListBinding::bind)
    private val viewModel: ItemListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (mContext as MainActivity).onBackPressedDispatcher.addCallback(this) {
            (mContext as MainActivity).mFinish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(ItemListFragmentDirections.actionItemListFragmentToItemAddFragment())
        }

        binding.btnDetail.setOnClickListener {
            findNavController().navigate(ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment())
        }
    }
}