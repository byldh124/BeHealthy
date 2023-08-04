package com.moondroid.behealthy.view.ui.item

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.moondroid.behealthy.R
import com.moondroid.behealthy.databinding.FragmentItemCountBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment

class ItemCountFragment : BaseFragment(R.layout.fragment_item_count) {
    private val binding by viewBinding(FragmentItemCountBinding::bind)

    private val args by navArgs<ItemCountFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.itemType = args.itemType
    }
}