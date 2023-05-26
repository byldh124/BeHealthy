package com.moondroid.behealthy.domain.usecase.user

import com.moondroid.behealthy.domain.repository.AppRepository
import javax.inject.Inject

class SignUseCase @Inject constructor(private val appRepository: AppRepository){
    suspend fun execute(id: String, name: String, thumb: String, token: String, type: Int) =
        appRepository.sign(id, name, thumb, token, type)

    suspend operator fun invoke(id: String, name: String, thumb: String, token: String, type: Int) =
        appRepository.sign(id, name, thumb, token, type)
}