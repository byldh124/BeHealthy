package com.moondroid.healthy.data.network

import com.moondroid.healthy.data.model.dto.BaseResponseDTO
import com.moondroid.healthy.data.network.response.URLManager
import com.moondroid.healthy.domain.model.status.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET(URLManager.CHECK_VERSION)
    suspend fun checkAppVersion(
        @Query("versionCode") versionCode: Int,
        @Query("packageName") packageName: String,
    ): ApiResult<BaseResponseDTO>
}