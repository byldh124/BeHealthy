package com.moondroid.healthy.data.mapper

import com.moondroid.healthy.data.model.dto.BaseResponseDTO
import com.moondroid.healthy.domain.model.BaseResponse

object DataMapper {
    fun BaseResponseDTO.toBaseResponse(): BaseResponse = BaseResponse(code, message)
}