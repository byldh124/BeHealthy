package com.moondroid.healthy.data.datasource.remote

import com.moondroid.healthy.data.model.dto.BaseResponseDTO
import com.moondroid.healthy.data.network.ApiInterface
import com.moondroid.healthy.domain.model.status.ApiResult
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface) : RemoteDataSource {
    override suspend fun checkAppVersion(
        versionCode: Int,
        packageName: String,
    ): ApiResult<BaseResponseDTO> = api.checkAppVersion(versionCode, packageName)
}