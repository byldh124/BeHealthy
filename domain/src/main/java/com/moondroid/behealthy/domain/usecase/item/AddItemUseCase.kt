package com.moondroid.behealthy.domain.usecase.item

import com.moondroid.behealthy.domain.repository.ItemRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(private val repository: ItemRepository) {
    /*suspend fun execute() = getItems()
    suspend operator fun invoke() = getItems()
    private suspend fun getItems() = repository.addItem()*/
}