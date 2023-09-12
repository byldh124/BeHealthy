package com.moondroid.legacy.domain.repository

import com.moondroid.legacy.common.ItemType
import com.moondroid.legacy.domain.model.Item
import com.moondroid.legacy.domain.model.status.ApiResult
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