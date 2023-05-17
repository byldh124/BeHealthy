package com.moondroid.healthy.data.model.dto

import com.google.gson.annotations.SerializedName

data class BaseResponseDTO(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String?
)