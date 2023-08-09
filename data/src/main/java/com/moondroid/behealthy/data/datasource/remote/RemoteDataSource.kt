package com.moondroid.behealthy.data.datasource.remote

import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.data.model.dto.ItemDTO
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
        type: Int,
    ): ApiResult<ProfileEntity>

    suspend fun updateToken(
        id: String,
        token: String,
    ): ApiResult<BaseResponseDTO>

    suspend fun getItems(
        id: String,
    ): ApiResult<List<ItemDTO>>

    suspend fun getSaying(): ApiResult<List<String>>
    suspend fun addItem(
        id: String,
        type: Int,
        startDate: Long,
        amount: Float,
        cost: Long,
        boxColor: Int
    ): ApiResult<BaseResponseDTO>
}