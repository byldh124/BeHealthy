package com.moondroid.legacy.data.datasource.remote

import com.moondroid.legacy.data.model.dto.ItemDTO
import com.moondroid.legacy.data.model.entity.ProfileEntity
import com.moondroid.legacy.data.model.request.AddItemRequest
import com.moondroid.legacy.data.model.request.SignRequest
import com.moondroid.legacy.data.model.request.UpdateTokenRequest
import com.moondroid.legacy.domain.model.status.ApiResult

interface RemoteDataSource {
    suspend fun checkAppVersion(versionCode: Int, versionName: String, packageName: String): ApiResult<Unit>

    suspend fun sign(signRequest: SignRequest): ApiResult<ProfileEntity>

    suspend fun updateToken(updateTokenRequest: UpdateTokenRequest): ApiResult<Unit>

    suspend fun getItems(id: String): ApiResult<List<ItemDTO>>

    suspend fun getSaying(): ApiResult<List<String>>

    suspend fun addItem(addItemRequest: AddItemRequest): ApiResult<Unit>

    suspend fun changeBoxColor(index: Int, boxColor: Int): ApiResult<Unit>
}