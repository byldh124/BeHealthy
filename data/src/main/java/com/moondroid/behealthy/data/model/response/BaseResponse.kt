package com.moondroid.behealthy.data.model.response

import com.moondroid.behealthy.common.ResponseCode

data class BaseResponse(
    val code: Int = 0,
    val message: String = "",
) {
    fun success() = code == ResponseCode.SUCCESS
}