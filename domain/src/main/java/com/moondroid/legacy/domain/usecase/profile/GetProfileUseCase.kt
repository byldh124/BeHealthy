package com.moondroid.legacy.domain.usecase.profile

import com.moondroid.legacy.domain.repository.AppRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute() = appRepository.getProfile()
    suspend operator fun invoke()= appRepository.getProfile()
}