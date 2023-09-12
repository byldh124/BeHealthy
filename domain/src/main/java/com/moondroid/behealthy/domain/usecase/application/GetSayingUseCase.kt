package com.moondroid.behealthy.domain.usecase.application

import com.moondroid.behealthy.domain.repository.AppRepository
import javax.inject.Inject

class GetSayingUseCase @Inject constructor(private val repository: AppRepository) {
    suspend fun execute() = repository.getSaying()
    suspend operator fun invoke() = repository.getSaying()
}