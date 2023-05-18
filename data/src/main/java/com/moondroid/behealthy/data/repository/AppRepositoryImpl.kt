package com.moondroid.behealthy.data.repository

import com.moondroid.behealthy.data.datasource.remote.RemoteDataSource
import com.moondroid.behealthy.data.mapper.DataMapper.toBaseResponse
import com.moondroid.behealthy.domain.model.BaseResponse
import com.moondroid.behealthy.domain.model.status.ApiResult
import com.moondroid.behealthy.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    AppRepository {
    override suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): Flow<ApiResult<BaseResponse>> {
        return flow<ApiResult<BaseResponse>> {
            remoteDataSource.checkAppVersion(versionCode, versionName, packageName).run {
                when (this) {
                    is ApiResult.Success -> emit(ApiResult.Success(response.toBaseResponse()))
                    is ApiResult.ApiError -> emit(ApiResult.ApiError(message, code))
                    is ApiResult.NetworkError -> emit(ApiResult.NetworkError(throwable))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}