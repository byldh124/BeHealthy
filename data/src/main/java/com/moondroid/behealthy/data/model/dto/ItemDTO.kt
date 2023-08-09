package com.moondroid.behealthy.data.model.dto

data class ItemDTO(
    val index: Int,
    val id: String,
    val type: Int,
    val startDate: Long,
    val amount: Float,
    val cost: Long,
    val boxColor: Int
)