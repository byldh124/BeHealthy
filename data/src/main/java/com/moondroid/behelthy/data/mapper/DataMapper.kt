package com.moondroid.behelthy.data.mapper

import com.moondroid.behelthy.data.model.dto.ItemDTO
import com.moondroid.behelthy.data.model.dto.ProfileDTO
import com.moondroid.behelthy.data.model.entity.ProfileEntity
import com.moondroid.behelthy.domain.model.Item
import com.moondroid.behelthy.domain.model.Profile

object DataMapper {
    fun ProfileDTO.toProfileEntity(): ProfileEntity = ProfileEntity(id, name, thumb, type)
    fun ProfileEntity.toProfile(): Profile = Profile(id, name, thumb, type)

    fun ItemDTO.toItem(): Item = Item(index, id, type, startDate, amount, cost, boxColor)
}