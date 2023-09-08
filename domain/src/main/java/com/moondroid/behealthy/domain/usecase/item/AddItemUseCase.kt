package com.moondroid.behealthy.domain.usecase.item

import com.moondroid.behealthy.common.ItemType
import com.moondroid.behealthy.domain.repository.ItemRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(private val repository: ItemRepository) {
    suspend fun addItem(id: String, type: ItemType, startDate: Long, amount: Float, cost: Long, boxColor: Int) =
        repository.addItem(id, type, startDate, amount, cost, boxColor)
}