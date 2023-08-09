package com.moondroid.behealthy.data.datasource.remote

import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.data.api.ApiInterface
import com.moondroid.behealthy.data.api.response.ApiStatus
import com.moondroid.behealthy.data.mapper.DataMapper.toProfileEntity
import com.moondroid.behealthy.data.model.dto.BaseResponseDTO
import com.moondroid.behealthy.data.model.dto.ItemDTO
import com.moondroid.behealthy.data.model.entity.ProfileEntity
import com.moondroid.behealthy.data.model.request.AddItemRequest
import com.moondroid.behealthy.data.model.request.SignRequest
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

    override suspend fun addItem(
        id: String,
        type: Int,
        startDate: Long,
        amount: Float,
        cost: Long,
        boxColor: Int
    ): ApiResult<BaseResponseDTO> {
        return when (val response = api.addItem(AddItemRequest(id, type, startDate, amount, cost, boxColor))) {
            is ApiStatus.Success -> {
                response.response.run {
                    if (code == ResponseCode.SUCCESS) ApiResult.Success(response.response)
                    else ApiResult.Fail(code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(response.throwable)
        }
    }

    override suspend fun getItems(id: String): ApiResult<List<ItemDTO>> {
        return when (val response = api.getItems(id)) {
            is ApiStatus.Success -> {
                response.response.run {
                    if (code == ResponseCode.SUCCESS) ApiResult.Success(response.response.result)
                    else ApiResult.Fail(code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(response.throwable)
        }
    }

    override suspend fun getSaying(): ApiResult<List<String>> {
        return when (val response = api.getSaying()) {
            is ApiStatus.Success -> {
                response.response.run {
                    if (code == ResponseCode.SUCCESS) ApiResult.Success(response.response.result)
                    else ApiResult.Fail(code)
                }
            }

            is ApiStatus.Error -> ApiResult.Error(response.throwable)
        }
    }
}