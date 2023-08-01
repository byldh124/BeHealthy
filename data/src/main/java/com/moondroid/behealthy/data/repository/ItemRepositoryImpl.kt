package com.moondroid.behealthy.data.repository

import com.moondroid.behealthy.data.datasource.remote.RemoteDataSource
import com.moondroid.behealthy.domain.model.BaseResponse
import com.moondroid.behealthy.domain.model.GoalItem
import com.moondroid.behealthy.domain.model.status.ApiResult
import com.moondroid.behealthy.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    ItemRepository {
    override suspend fun getItems(id: String): Flow<ApiResult<List<GoalItem>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addItem(): Flow<ApiResult<BaseResponse>> {
        TODO("Not yet implemented")
    }
}