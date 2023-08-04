package com.moondroid.behealthy.view.ui.item

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.moondroid.behealthy.R
import com.moondroid.behealthy.databinding.FragmentItemCountBinding
import com.moondroid.behealthy.databinding.LayoutItemAddDrkBinding
import com.moondroid.behealthy.databinding.LayoutItemAddSmkBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment

class ItemCountFragment : BaseFragment(R.layout.fragment_item_count) {
    private val binding by viewBinding(FragmentItemCountBinding::bind)

    private var _smkBinding: LayoutItemAddSmkBinding? = null
    private val smkBinding get() = _smkBinding!!

    private var _drkBinding: LayoutItemAddDrkBinding? = null
    private val drkBinding get() = _drkBinding!!

    private val args by navArgs<ItemCountFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.itemType = args.itemType

        _smkBinding = LayoutItemAddSmkBinding.bind(binding.smkLayout.root)
        _drkBinding = LayoutItemAddDrkBinding.bind(binding.drkLayout.root)

        initView()
    }

    private fun initView() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _smkBinding = null
        _drkBinding = null
    }
}