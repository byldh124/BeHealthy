package com.moondroid.legacy.data.mapper

import com.moondroid.legacy.data.model.dto.ItemDTO
import com.moondroid.legacy.data.model.dto.ProfileDTO
import com.moondroid.legacy.data.model.entity.ProfileEntity
import com.moondroid.legacy.domain.model.Item
import com.moondroid.legacy.domain.model.Profile

object DataMapper {
    fun ProfileDTO.toProfileEntity(): ProfileEntity = ProfileEntity(id, name, thumb, type)
    fun ProfileEntity.toProfile(): Profile = Profile(id, name, thumb, type)

    fun ItemDTO.toItem(): Item = Item(index, id, type, startDate, amount, cost, boxColor)
}