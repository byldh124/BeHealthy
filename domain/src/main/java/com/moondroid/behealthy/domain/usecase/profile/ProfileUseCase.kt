package com.moondroid.behealthy.domain.usecase.profile

import com.moondroid.behealthy.domain.repository.AppRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute() = appRepository.getProfile()
    suspend operator fun invoke()= appRepository.getProfile()
}