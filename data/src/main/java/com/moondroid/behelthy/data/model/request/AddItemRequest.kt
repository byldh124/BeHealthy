package com.moondroid.behelthy.data.model.request

import com.moondroid.behelthy.common.ItemType

data class AddItemRequest(
    val id: String,
    val type: ItemType,
    val startDate: Long,
    val amount: Float,
    val cost: Long,
    val boxColor: Int,
)