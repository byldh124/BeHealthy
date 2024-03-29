package com.moondroid.behealthy.data.repository

import com.moondroid.behealthy.common.ItemType
import com.moondroid.behealthy.data.datasource.remote.RemoteDataSource
import com.moondroid.behealthy.data.mapper.DataMapper.toItem
import com.moondroid.behealthy.data.model.request.AddItemRequest
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.domain.model.status.ApiResult
import com.moondroid.behealthy.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    ItemRepository {
    override suspend fun getItems(id: String): Flow<ApiResult<List<Item>>> {
        return flow {
            remoteDataSource.getItems(id).run {
                when (this) {
                    is ApiResult.Success -> emit(ApiResult.Success(response.map { it.toItem() }))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                }
            }
        }
    }

    override suspend fun addItem(
        id: String,
        type: ItemType,
        startDate: Long,
        amount: Float,
        cost: Long,
        boxColor: Int,
    ): Flow<ApiResult<Unit>> = flow {
        emit(remoteDataSource.addItem(AddItemRequest(id, type, startDate, amount, cost, boxColor)))
    }.flowOn(Dispatchers.IO)

    override suspend fun changeBoxColor(index: Int, boxColor: Int): Flow<ApiResult<Unit>> = flow {
        emit(remoteDataSource.changeBoxColor(index, boxColor))
    }.flowOn(Dispatchers.IO)
}