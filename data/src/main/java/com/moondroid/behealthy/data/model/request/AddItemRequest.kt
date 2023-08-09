package com.moondroid.behealthy.data.model.request

data class AddItemRequest(
    val id: String,
    val type: Int,
    val startDate: Long,
    val amount: Float,
    val cost: Long,
    val boxColor: Int
)