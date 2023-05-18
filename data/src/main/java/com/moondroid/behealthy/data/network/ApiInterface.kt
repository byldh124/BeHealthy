package com.moondroid.behealthy.data.network

import com.moondroid.behealthy.common.ApiParam
import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.data.network.response.URLManager
import com.moondroid.behealthy.domain.model.status.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(URLManager.CHECK_VERSION)
    suspend fun checkAppVersion(
        @Query(ApiParam.VERSION_CODE) versionCode: Int,
        @Query(ApiParam.VERSION_NAME) versionName: String,
        @Query(ApiParam.PACKAGE_NAME) packageName: String,
    ): ApiResult<BaseResponseDTO>
}