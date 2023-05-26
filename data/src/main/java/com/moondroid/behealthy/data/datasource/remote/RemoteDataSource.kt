package com.moondroid.behealthy.data.datasource.remote

import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.data.model.entity.ProfileEntity
import com.moondroid.behealthy.domain.model.status.ApiResult

interface RemoteDataSource {
    suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): ApiResult<BaseResponseDTO>

    suspend fun sign(
        id: String,
        name: String,
        thumb: String,
        token: String,
        type: Int
    ): ApiResult<ProfileEntity>
}