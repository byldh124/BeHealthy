package com.moondroid.behealthy.domain.repository

import com.moondroid.behealthy.domain.model.BaseResponse
import com.moondroid.behealthy.domain.model.Profile
import com.moondroid.behealthy.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun checkAppVersion(versionCode: Int, versionName: String, packageName: String) : Flow<ApiResult<BaseResponse>>
    suspend fun sign(id: String, name: String, thumb: String, token: String, type: Int) : Flow<ApiResult<Profile>>
}