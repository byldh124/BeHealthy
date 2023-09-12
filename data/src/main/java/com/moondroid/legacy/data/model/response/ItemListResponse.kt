package com.moondroid.legacy.data.model.response

import com.moondroid.legacy.common.ResponseCode
import com.moondroid.legacy.data.model.dto.ItemDTO

data class ItemListResponse(
    val code: Int,
    val message: String,
    val result: List<ItemDTO>,
) {
    fun success() = code ==ResponseCode.SUCCESS
}