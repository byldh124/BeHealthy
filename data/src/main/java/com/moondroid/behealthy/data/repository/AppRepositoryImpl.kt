package com.moondroid.behealthy.data.repository

import com.moondroid.behealthy.data.datasource.local.LocalDataSource
import com.moondroid.behealthy.data.datasource.remote.RemoteDataSource
import com.moondroid.behealthy.data.mapper.DataMapper.toProfile
import com.moondroid.behealthy.data.model.request.SignRequest
import com.moondroid.behealthy.data.model.request.UpdateTokenRequest
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
    private val localDataSource: LocalDataSource,
) : AppRepository {
    override suspend fun checkAppVersion(versionCode: Int, versionName: String, packageName: String) =
        flow {
            emit(remoteDataSource.checkAppVersion(versionCode, versionName, packageName))
        }.flowOn(Dispatchers.IO)

    override suspend fun sign(id: String, name: String, thumb: String, type: Int) =
        flow<ApiResult<Profile>> {
            remoteDataSource.sign(SignRequest(id, name, thumb, type)).run {
                when (this) {
                    is ApiResult.Success -> {
                        localDataSource.deleteAllProfile()
                        localDataSource.insertProfile(response)
                        emit(ApiResult.Success(response.toProfile()))
                    }

                    is ApiResult.Fail -> emit(ApiResult.Fail(code))
                    is ApiResult.Error -> emit(ApiResult.Error(throwable))
                }
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getProfile(): Flow<Profile?> {
        return flow {
            localDataSource.getProfile().run {
                if (isEmpty()) emit(null)
                else emit(last().toProfile())
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateToken(id: String, token: String) = flow {
        emit(remoteDataSource.updateToken(UpdateTokenRequest(id, token)))
    }.flowOn(Dispatchers.IO)

    override suspend fun isTutorial(): Flow<Boolean> {
        return flow {
            emit(localDataSource.isTutorial())
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSaying() = flow {
        emit(remoteDataSource.getSaying())
    }.flowOn(Dispatchers.IO)
}