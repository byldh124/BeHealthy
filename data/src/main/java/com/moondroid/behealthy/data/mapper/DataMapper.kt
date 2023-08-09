package com.moondroid.behealthy.data.mapper

import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.data.model.dto.ItemDTO
import com.moondroid.behealthy.data.model.dto.ProfileDTO
import com.moondroid.behealthy.data.model.entity.ProfileEntity
import com.moondroid.behealthy.domain.model.BaseResponse
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.domain.model.Profile

object DataMapper {
    fun BaseResponseDTO.toBaseResponse(): BaseResponse = BaseResponse(code, message)
    fun ProfileDTO.toProfileEntity(): ProfileEntity = ProfileEntity(id, name, thumb, type)
    fun ProfileEntity.toProfile(): Profile = Profile(id, name, thumb, type)

    fun ItemDTO.toItem(): Item = Item(index, id, type, startDate, amount, cost, boxColor)
}