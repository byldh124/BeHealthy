package com.moondroid.behealthy.data.datasource.remote

import com.moondroid.behealthy.data.mapper.DataMapper.toProfileEntity
import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.data.model.entity.ProfileEntity
import com.moondroid.behealthy.data.model.request.SignRequest
import com.moondroid.behealthy.data.network.ApiInterface
import com.moondroid.behealthy.domain.model.status.ApiResult
import com.moondroid.behealthy.domain.model.status.onApiError
import com.moondroid.behealthy.domain.model.status.onNetworkError
import com.moondroid.behealthy.domain.model.status.onSuccess
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface) : RemoteDataSource {
    override suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): ApiResult<BaseResponseDTO> = api.checkAppVersion(versionCode, versionName, packageName)

    override suspend fun sign(
        id: String,
        name: String,
        thumb: String,
        token: String,
        type: Int,
    ): ApiResult<ProfileEntity> {
        return when(val response = api.sign(SignRequest(id, name, thumb, token, type))) {
            is ApiResult.Success -> ApiResult.Success(response.response.result.toProfileEntity())
            is ApiResult.NetworkError -> ApiResult.NetworkError(response.throwable)
            is ApiResult.ApiError -> ApiResult.ApiError(response.code)
        }
    }
}