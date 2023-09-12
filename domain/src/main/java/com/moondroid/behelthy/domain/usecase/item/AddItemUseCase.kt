package com.moondroid.behelthy.domain.usecase.item

import com.moondroid.behelthy.common.ItemType
import com.moondroid.behelthy.domain.repository.ItemRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(private val repository: ItemRepository) {
    suspend fun addItem(id: String, type: ItemType, startDate: Long, amount: Float, cost: Long, boxColor: Int) =
        repository.addItem(id, type, startDate, amount, cost, boxColor)
}