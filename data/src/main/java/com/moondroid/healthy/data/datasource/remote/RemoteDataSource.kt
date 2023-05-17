package com.moondroid.healthy.data.datasource.remote

import com.moondroid.healthy.data.model.dto.BaseResponseDTO
import com.moondroid.healthy.domain.model.status.ApiResult

interface RemoteDataSource {
    suspend fun checkAppVersion(versionCode: Int, packageName: String): ApiResult<BaseResponseDTO>
}