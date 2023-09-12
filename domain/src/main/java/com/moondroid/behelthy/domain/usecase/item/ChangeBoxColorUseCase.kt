package com.moondroid.behelthy.domain.usecase.item

import com.moondroid.behelthy.domain.repository.ItemRepository
import javax.inject.Inject

class ChangeBoxColorUseCase @Inject constructor(private val repository: ItemRepository) {
    suspend operator fun invoke(index: Int, boxColor: Int) = changeBoxColor(index, boxColor)
    private suspend fun changeBoxColor(index: Int, boxColor: Int) = repository.changeBoxColor(index, boxColor)
}