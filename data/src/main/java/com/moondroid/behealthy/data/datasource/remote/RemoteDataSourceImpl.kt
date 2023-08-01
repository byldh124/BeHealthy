package com.moondroid.behealthy.data.datasource.remote

import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.data.mapper.DataMapper.toProfileEntity
import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.data.model.entity.ProfileEntity
import com.moondroid.behealthy.data.model.request.SignRequest
import com.moondroid.behealthy.data.api.ApiInterface
import com.moondroid.behealthy.data.api.response.ApiStatus
import com.moondroid.behealthy.data.model.request.UpdateTokenRequest
import com.moondroid.behealthy.domain.model.status.ApiResult
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface) : RemoteDataSource {
    override suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): ApiResult<BaseResponseDTO> {
        return when (val result = api.checkAppVersion(versionCode, versionName, packageName)) {
            is ApiStatus.Success -> {
                result.response.run {
                    if (code == ResponseCode.SUCCESS) {
                        ApiResult.Success(this)
                    } else {
                        ApiResult.Fail(code)
                    }
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun sign(
        id: String,
        name: String,
        thumb: String,
        type: Int,
    ): ApiResult<ProfileEntity> {
        return when (val result = api.sign(SignRequest(id, name, thumb, type))) {
            is ApiStatus.Success -> {
                result.response.run {
                    if (code == ResponseCode.SUCCESS) {
                        ApiResult.Success(this.result.toProfileEntity())
                    } else {
                        ApiResult.Fail(code)
                    }
                }
            }

            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }

    override suspend fun updateToken(id: String, token: String): ApiResult<BaseResponseDTO> {
        return when (val result = api.updateToken(UpdateTokenRequest(id, token))) {
            is ApiStatus.Success -> {
                result.response.run {
                    if (code == ResponseCode.SUCCESS) {
                        ApiResult.Success(this)
                    } else {
                        ApiResult.Fail(code)
                    }
                }
            }
            is ApiStatus.Error -> ApiResult.Error(result.throwable)
        }
    }
}