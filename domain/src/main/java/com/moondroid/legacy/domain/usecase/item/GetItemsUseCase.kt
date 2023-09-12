package com.moondroid.legacy.domain.usecase.item

import com.moondroid.legacy.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemsUseCase @Inject constructor(private val repository: ItemRepository) {
    suspend fun execute(id: String) = getItems(id)
    suspend operator fun invoke(id: String) = getItems(id)
    private suspend fun getItems(id: String) = repository.getItems(id)
}