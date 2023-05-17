package com.moondroid.healthy.domain.repository

import com.moondroid.healthy.domain.model.BaseResponse
import com.moondroid.healthy.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun checkAppVersion(versionCode: Int, packageName: String) : Flow<ApiResult<BaseResponse>>
}