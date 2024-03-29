package com.moondroid.behealthy.domain.usecase.profile

import com.moondroid.behealthy.domain.repository.AppRepository
import javax.inject.Inject

class SignUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(id: String, name: String, thumb: String, type: Int) =
        appRepository.sign(id, name, thumb, type)

    suspend operator fun invoke(id: String, name: String, thumb: String, type: Int) =
        appRepository.sign(id, name, thumb, type)
}