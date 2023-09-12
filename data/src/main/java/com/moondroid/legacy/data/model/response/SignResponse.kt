package com.moondroid.legacy.data.model.response

import com.moondroid.legacy.common.ResponseCode
import com.moondroid.legacy.data.model.dto.ProfileDTO

data class SignResponse(
    val code: Int,
    val message: String,
    val result: ProfileDTO,
) {
    fun success() = code == ResponseCode.SUCCESS
}