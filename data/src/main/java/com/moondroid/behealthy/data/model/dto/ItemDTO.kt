package com.moondroid.behealthy.data.model.dto

import com.google.gson.annotations.SerializedName
import com.moondroid.behealthy.common.ItemType

data class ItemDTO(
    @SerializedName("idx")
    val index: Int,
    val id: String,
    val type: ItemType,
    val startDate: Long,
    val amount: Float,
    val cost: Long,
    val boxColor: Int
)