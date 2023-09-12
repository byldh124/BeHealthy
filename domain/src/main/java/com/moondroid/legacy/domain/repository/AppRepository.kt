package com.moondroid.legacy.domain.repository

import com.moondroid.legacy.domain.model.Profile
import com.moondroid.legacy.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): Flow<ApiResult<Unit>>

    suspend fun sign(id: String, name: String, thumb: String, type: Int): Flow<ApiResult<Profile>>
    suspend fun getProfile(): Flow<Profile?>
    suspend fun updateToken(id: String, token: String): Flow<ApiResult<Unit>>
    suspend fun isTutorial(): Flow<Boolean>
    suspend fun getSaying(): Flow<ApiResult<List<String>>>
}