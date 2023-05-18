package com.moondroid.behealthy.domain.model.status

sealed class ApiResult<out T> {
    data class Success<out T>(val response: T) : ApiResult<T>()
    data class ApiError<T>(val code: Int) : ApiResult<T>()
    data class NetworkError<T>(val throwable: Throwable) : ApiResult<T>()
}

inline fun <T : Any> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(response)
    return this
}

inline fun <T : Any> ApiResult<T>.onApiError(action: (Int) -> Unit): ApiResult<T> {
    if (this is ApiResult.ApiError) action(code)
    return this
}

inline fun <T : Any> ApiResult<T>.onNetworkError(action: (Throwable) -> Unit): ApiResult<T> {
    if (this is ApiResult.NetworkError) action(throwable)
    return this
}