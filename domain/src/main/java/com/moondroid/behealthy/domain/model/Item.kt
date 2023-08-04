package com.moondroid.behealthy.domain.model

data class Item(
    val index: Int,
    val id: String,
    val type: Int,
    val startDate: Long,
    val countOfWeek: Int,
    val costOfWeek: Long,
)