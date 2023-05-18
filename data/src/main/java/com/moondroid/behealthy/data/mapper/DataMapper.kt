package com.moondroid.behealthy.data.mapper

import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.domain.model.BaseResponse

object DataMapper {
    fun BaseResponseDTO.toBaseResponse(): BaseResponse = BaseResponse(code, message)
}