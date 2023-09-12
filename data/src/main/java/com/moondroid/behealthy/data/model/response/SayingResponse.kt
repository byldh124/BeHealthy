package com.moondroid.behealthy.data.model.response

import com.moondroid.behealthy.common.ResponseCode

data class SayingResponse(
    val code: Int,
    val message: String,
    val result: List<String>,
) {
    fun success() = code == ResponseCode.SUCCESS
}