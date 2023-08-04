package com.moondroid.behealthy.domain.usecase.application

import com.moondroid.behealthy.domain.repository.AppRepository
import javax.inject.Inject

class TutorialUseCase @Inject constructor(private val repository: AppRepository) {
    suspend fun execute() = repository.isTutorial()
    suspend operator fun invoke() = repository.isTutorial()
}