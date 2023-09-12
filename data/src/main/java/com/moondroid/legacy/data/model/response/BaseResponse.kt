package com.moondroid.legacy.data.model.response

import com.moondroid.legacy.common.ResponseCode

data class BaseResponse(
    val code: Int = 0,
    val message: String = "",
) {
    fun success() = code == ResponseCode.SUCCESS
}