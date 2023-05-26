package com.moondroid.behealthy.data.model.request

data class SignRequest(
    val id: String,
    val name: String,
    val thumb: String,
    val token: String,
    val type: Int
)