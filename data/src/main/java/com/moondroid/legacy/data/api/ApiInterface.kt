package com.moondroid.legacy.data.api

import com.moondroid.legacy.common.ApiParam
import com.moondroid.legacy.data.api.response.ApiStatus
import com.moondroid.legacy.data.model.request.AddItemRequest
import com.moondroid.legacy.data.model.request.SignRequest
import com.moondroid.legacy.data.model.request.UpdateTokenRequest
import com.moondroid.legacy.data.model.response.BaseResponse
import com.moondroid.legacy.data.model.response.ItemListResponse
import com.moondroid.legacy.data.model.response.SayingResponse
import com.moondroid.legacy.data.model.response.SignResponse
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
    ): ApiStatus<BaseResponse>

    @POST(URLManager.SIGN)
    suspend fun sign(@Body body: SignRequest): ApiStatus<SignResponse>

    @POST(URLManager.UPDATE_TOKEN)
    suspend fun updateToken(@Body body: UpdateTokenRequest): ApiStatus<BaseResponse>

    @POST(URLManager.ADD_ITEM)
    suspend fun addItem(@Body body: AddItemRequest): ApiStatus<BaseResponse>

    @GET(URLManager.GET_ITEMS)
    suspend fun getItems(@Query(ApiParam.ID) id: String): ApiStatus<ItemListResponse>

    @GET(URLManager.GET_SAYING)
    suspend fun getSaying(): ApiStatus<SayingResponse>

    @GET(URLManager.CHANGE_BOX)
    suspend fun changeBoxColor(
        @Query(ApiParam.INDEX) index: Int,
        @Query(ApiParam.BOX_COLOR) boxColor: Int,
    ): ApiStatus<BaseResponse>
}