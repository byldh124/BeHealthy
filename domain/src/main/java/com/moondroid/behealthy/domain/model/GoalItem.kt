package com.moondroid.behealthy.domain.model

data class GoalItem(
    val index: Long,
    val id: String,
    val type: Int,
    val startDate: Long,
    val countOfWeek: Int,
    val priceOfWeek: Long,
)