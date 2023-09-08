package com.moondroid.behealthy.domain.repository

import com.moondroid.behealthy.common.ItemType
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.domain.model.status.ApiResult
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun getItems(id: String): Flow<ApiResult<List<Item>>>

    suspend fun addItem(
        id: String,
        type: ItemType,
        startDate: Long,
        amount: Float,
        cost: Long,
        boxColor: Int,
    ): Flow<ApiResult<Unit>>

    suspend fun changeBoxColor(index: Int, boxColor: Int): Flow<ApiResult<Unit>>
}