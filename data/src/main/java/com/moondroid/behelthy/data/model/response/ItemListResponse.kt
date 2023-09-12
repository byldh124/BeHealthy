package com.moondroid.behelthy.data.model.response

import com.moondroid.behelthy.common.ResponseCode
import com.moondroid.behelthy.data.model.dto.ItemDTO

data class ItemListResponse(
    val code: Int,
    val message: String,
    val result: List<ItemDTO>,
) {
    fun success() = code ==ResponseCode.SUCCESS
}