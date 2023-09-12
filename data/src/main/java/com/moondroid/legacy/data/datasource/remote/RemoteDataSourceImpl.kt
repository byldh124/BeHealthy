package com.moondroid.legacy.data.datasource.remote

import com.moondroid.legacy.data.api.ApiInterface
import com.moondroid.legacy.data.api.response.ApiStatus
import com.moondroid.legacy.data.mapper.DataMapper.toProfileEntity
import com.moondroid.legacy.data.model.dto.ItemDTO
import com.moondroid.legacy.data.model.entity.ProfileEntity
import com.moondroid.legacy.data.model.request.AddItemRequest
import com.moondroid.legacy.data.model.request.SignRequest
import com.moondroid.legacy.data.model.request.UpdateTokenRequest
import com.moondroid.legacy.domain.model.status.ApiResult
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