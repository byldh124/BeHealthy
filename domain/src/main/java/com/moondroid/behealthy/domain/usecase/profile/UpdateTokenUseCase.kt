package com.moondroid.behealthy.domain.usecase.profile

import com.moondroid.behealthy.domain.repository.AppRepository
import javax.inject.Inject

class UpdateTokenUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(id: String, token: String) = appRepository.updateToken(id, token)
    suspend operator fun invoke(id: String, token: String) = appRepository.updateToken(id, token)
}