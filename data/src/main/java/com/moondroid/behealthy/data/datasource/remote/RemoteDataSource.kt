package com.moondroid.behealthy.data.datasource.remote

import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.domain.model.status.ApiResult

interface RemoteDataSource {
    suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): ApiResult<BaseResponseDTO>
}