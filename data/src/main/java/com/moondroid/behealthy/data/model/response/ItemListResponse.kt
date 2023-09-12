package com.moondroid.behealthy.data.model.response

import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.data.model.dto.ItemDTO

data class ItemListResponse(
    val code: Int,
    val message: String,
    val result: List<ItemDTO>,
) {
    fun success() = code ==ResponseCode.SUCCESS
}