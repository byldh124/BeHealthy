package com.moondroid.behelthy.domain.repository

import com.moondroid.behelthy.common.ItemType
import com.moondroid.behelthy.domain.model.Item
import com.moondroid.behelthy.domain.model.status.ApiResult
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