package com.moondroid.healthy.domain.usecase.application

import com.moondroid.healthy.domain.repository.AppRepository
import javax.inject.Inject

class AppVersionUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(versionCode: Int, packageName: String) = appRepository.checkAppVersion(versionCode, packageName)
    suspend operator fun invoke(versionCode: Int, packageName: String) = appRepository.checkAppVersion(versionCode, packageName)
}