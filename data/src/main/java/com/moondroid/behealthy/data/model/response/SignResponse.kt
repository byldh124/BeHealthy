package com.moondroid.behealthy.data.model.response

import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.data.model.dto.ProfileDTO

data class SignResponse(
    val code: Int,
    val message: String,
    val result: ProfileDTO,
) {
    fun success() = code == ResponseCode.SUCCESS
}