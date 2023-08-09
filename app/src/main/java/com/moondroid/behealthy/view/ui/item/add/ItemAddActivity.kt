package com.moondroid.behealthy.view.ui.item.add

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.IntentParam
import com.moondroid.behealthy.databinding.ActivityItemAddBinding
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * ItemSelect -> ItemCount
 **/
@AndroidEntryPoint
class ItemAddActivity : BaseActivity() {
    private val binding by viewBinding(ActivityItemAddBinding::inflate)

    lateinit var list: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val json = intent.getStringExtra(IntentParam.ITEMS)
        list = Gson().fromJson(json, object : TypeToken<List<Item>>() {}.type)
    }

}