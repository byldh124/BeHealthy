package com.moondroid.behealthy.domain.repository

import com.moondroid.behealthy.domain.model.BaseResponse
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun getItems(id: String): Flow<ApiResult<List<Item>>>
    suspend fun addItem(): Flow<ApiResult<BaseResponse>>
}