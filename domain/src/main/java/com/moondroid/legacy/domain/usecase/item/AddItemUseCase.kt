package com.moondroid.legacy.domain.usecase.item

import com.moondroid.legacy.common.ItemType
import com.moondroid.legacy.domain.repository.ItemRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(private val repository: ItemRepository) {
    suspend fun addItem(id: String, type: ItemType, startDate: Long, amount: Float, cost: Long, boxColor: Int) =
        repository.addItem(id, type, startDate, amount, cost, boxColor)
}