package com.moondroid.behealthy.view.ui.item

import com.moondroid.behealthy.databinding.ActivityItemAddBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * ItemSelect -> ItemCount
 **/
@AndroidEntryPoint
class ItemAddActivity : BaseActivity() {
    private val binding by viewBinding(ActivityItemAddBinding::inflate)
}