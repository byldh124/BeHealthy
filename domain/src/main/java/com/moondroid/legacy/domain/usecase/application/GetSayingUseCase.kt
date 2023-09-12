package com.moondroid.legacy.domain.usecase.application

import com.moondroid.legacy.domain.repository.AppRepository
import javax.inject.Inject

class GetSayingUseCase @Inject constructor(private val repository: AppRepository) {
    suspend fun execute() = repository.getSaying()
    suspend operator fun invoke() = repository.getSaying()
}