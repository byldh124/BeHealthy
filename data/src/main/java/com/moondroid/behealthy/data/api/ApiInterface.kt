package com.moondroid.behealthy.data.api

import com.moondroid.behealthy.common.ApiParam
import com.moondroid.behealthy.data.api.response.ApiStatus
import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.data.model.request.SignRequest
import com.moondroid.behealthy.data.model.request.UpdateTokenRequest
import com.moondroid.behealthy.data.model.response.SignResponse
import com.moondroid.behealthy.domain.model.status.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @GET(URLManager.CHECK_VERSION)
    suspend fun checkAppVersion(
        @Query(ApiParam.VERSION_CODE) versionCode: Int,
        @Query(ApiParam.VERSION_NAME) versionName: String,
        @Query(ApiParam.PACKAGE_NAME) packageName: String,
    ): ApiStatus<BaseResponseDTO>

    @POST(URLManager.SIGN)
    suspend fun sign(
        @Body body: SignRequest,
    ): ApiStatus<SignResponse>

    @POST(URLManager.UPDATE_TOKEN)
    suspend fun updateToken(
        @Body body: UpdateTokenRequest,
    ): ApiStatus<BaseResponseDTO>
}