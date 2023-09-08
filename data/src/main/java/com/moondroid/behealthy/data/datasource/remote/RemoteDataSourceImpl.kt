package com.moondroid.behealthy.data.datasource.remote

import com.moondroid.behealthy.data.api.ApiInterface
import com.moondroid.behealthy.data.api.response.ApiStatus
import com.moondroid.behealthy.data.mapper.DataMapper.toProfileEntity
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
    ): ApiResult<Unit> {
        api.checkAppVersion(versionCode, versionName, packageName).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun sign(signRequest: SignRequest): ApiResult<ProfileEntity> {
        api.sign(signRequest).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.success()) ApiResult.Success(response.result.toProfileEntity())
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun updateToken(updateTokenRequest: UpdateTokenRequest): ApiResult<Unit> {
        api.updateToken(updateTokenRequest).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun getItems(id: String): ApiResult<List<ItemDTO>> {
        api.getItems(id).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun getSaying(): ApiResult<List<String>> {
        api.getSaying().run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.success()) ApiResult.Success(response.result)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun addItem(addItemRequest: AddItemRequest): ApiResult<Unit> {
        api.addItem(addItemRequest).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

    override suspend fun changeBoxColor(index: Int, boxColor: Int): ApiResult<Unit> {
        api.changeBoxColor(index, boxColor).run {
            return when (this) {
                is ApiStatus.Error -> ApiResult.Error(throwable)
                is ApiStatus.Success -> {
                    if (response.success()) ApiResult.Success(Unit)
                    else ApiResult.Fail(response.code)
                }
            }
        }
    }

}