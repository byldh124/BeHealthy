package com.moondroid.behealthy.data.model.response

data class SayingResponse(
    val code: Int,
    val message: String,
    val result: List<String>,
)