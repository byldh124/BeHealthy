package com.moondroid.behealthy.data.repository

import com.moondroid.behealthy.data.datasource.local.room.ProfileDao
import com.moondroid.behealthy.data.datasource.remote.RemoteDataSource
import com.moondroid.behealthy.data.mapper.DataMapper.toBaseResponse
import com.moondroid.behealthy.data.mapper.DataMapper.toProfile
import com.moondroid.behealthy.domain.model.BaseResponse
import com.moondroid.behealthy.domain.model.Profile
import com.moondroid.behealthy.domain.model.status.ApiResult
import com.moondroid.behealthy.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDatabase: ProfileDao,
) : AppRepository {
    override suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): Flow<ApiResult<BaseResponse>> {
        return flow<ApiResult<BaseResponse>> {
            remoteDataSource.checkAppVersion(versionCode, versionName, packageName).run {
                when (this) {
                    is ApiResult.Success -> emit(ApiResult.Success(response.toBaseResponse()))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun sign(
        id: String,
        name: String,
        thumb: String,
        type: Int,
    ): Flow<ApiResult<Profile>> {
        return flow<ApiResult<Profile>> {
            remoteDataSource.sign(id, name, thumb, type).run {
                when (this) {
                    is ApiResult.Success ->  {
                        localDatabase.deleteAll()
                        localDatabase.insertProfile(response)
                        emit(ApiResult.Success(response.toProfile()))
                    }
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getProfile(): Flow<Profile?> {
        return flow {
            localDatabase.getProfile().run {
                if (isEmpty()) emit(null)
                else emit(last().toProfile())
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateToken(id: String, token: String): Flow<ApiResult<BaseResponse>> {
        return flow {
            remoteDataSource.updateToken(id, token).run {
                when (this) {
                    is ApiResult.Success -> emit(ApiResult.Success(response.toBaseResponse()))
                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                }
            }
        }
    }
}