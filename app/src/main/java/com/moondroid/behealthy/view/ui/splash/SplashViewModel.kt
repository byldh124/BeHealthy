package com.moondroid.behealthy.view.ui.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.domain.model.status.onApiError
import com.moondroid.behealthy.domain.model.status.onNetworkError
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.application.AppVersionUseCase
import com.moondroid.behealthy.utils.MutableEventFlow
import com.moondroid.behealthy.utils.asEventFlow
import com.moondroid.behealthy.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appVersionUseCase: AppVersionUseCase,
) : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<SplashEvent>()
    val eventFLow = _eventFlow.asEventFlow()

    fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ) {
        viewModelScope.launch {
            delay(2000)
            appVersionUseCase(versionCode, versionName, packageName).collect { result ->
                result.onSuccess {
                    event(SplashEvent.Version(it.code))
                }.onApiError {
                    //TODO Handle Api Error
                }.onNetworkError {
                    it.logException()
                }
            }
        }
    }

    fun checkUserInfo() {
        //TODO GetUserInfo
        event(SplashEvent.Sign)
    }


    private fun event(event: SplashEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class SplashEvent {
        data class Version(val code: Int) : SplashEvent()
        object Sign: SplashEvent()
        object Home: SplashEvent()
    }
}