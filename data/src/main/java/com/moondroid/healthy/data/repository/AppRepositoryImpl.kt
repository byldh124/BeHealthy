package com.moondroid.healthy.data.repository

import com.moondroid.healthy.data.datasource.remote.RemoteDataSource
import com.moondroid.healthy.data.mapper.DataMapper.toBaseResponse
import com.moondroid.healthy.domain.model.BaseResponse
import com.moondroid.healthy.domain.model.status.ApiResult
import com.moondroid.healthy.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    AppRepository {
    override suspend fun checkAppVersion(
        versionCode: Int,
        packageName: String,
    ): Flow<ApiResult<BaseResponse>> {
        return flow<ApiResult<BaseResponse>> {
            remoteDataSource.checkAppVersion(versionCode, packageName).run {
                when (this) {
                    is ApiResult.Success -> emit(ApiResult.Success(response.toBaseResponse()))
                    is ApiResult.ApiError -> emit(ApiResult.ApiError(message, code))
                    is ApiResult.NetworkError -> emit(ApiResult.NetworkError(throwable))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}