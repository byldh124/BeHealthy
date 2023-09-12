package com.moondroid.legacy.domain.usecase.application

import com.moondroid.legacy.domain.repository.AppRepository
import javax.inject.Inject

class AppVersionUseCase @Inject constructor(private val appRepository: AppRepository) {
    suspend fun execute(versionCode: Int, versionName: String, packageName: String) =
        appRepository.checkAppVersion(versionCode, versionName, packageName)

    suspend operator fun invoke(versionCode: Int, versionName: String, packageName: String) =
        appRepository.checkAppVersion(versionCode, versionName, packageName)
}