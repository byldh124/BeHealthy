package com.moondroid.legacy.data.model.request

import com.moondroid.legacy.common.ItemType

data class AddItemRequest(
    val id: String,
    val type: ItemType,
    val startDate: Long,
    val amount: Float,
    val cost: Long,
    val boxColor: Int,
)