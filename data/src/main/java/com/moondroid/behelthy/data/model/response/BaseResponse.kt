package com.moondroid.behelthy.data.model.response

import com.moondroid.behelthy.common.ResponseCode

data class BaseResponse(
    val code: Int = 0,
    val message: String = "",
) {
    fun success() = code == ResponseCode.SUCCESS
}