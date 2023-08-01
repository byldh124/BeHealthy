package com.moondroid.behealthy.data.api.response

import com.moondroid.behealthy.domain.model.status.ApiResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class ResponseAdapter<T>(
    private val successType: Type,
) : CallAdapter<T, Call<ApiStatus<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<ApiStatus<T>> = ResponseCall(call)
}