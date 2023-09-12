package com.moondroid.legacy.data.model.response

import com.moondroid.legacy.common.ResponseCode

data class SayingResponse(
    val code: Int,
    val message: String,
    val result: List<String>,
) {
    fun success() = code == ResponseCode.SUCCESS
}