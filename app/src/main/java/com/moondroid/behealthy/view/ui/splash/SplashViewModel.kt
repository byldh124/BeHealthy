package com.moondroid.behealthy.view.ui.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.domain.model.Profile
import com.moondroid.behealthy.domain.model.status.onError
import com.moondroid.behealthy.domain.model.status.onFail
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.application.AppVersionUseCase
import com.moondroid.behealthy.domain.usecase.profile.ProfileUseCase
import com.moondroid.behealthy.utils.MutableEventFlow
import com.moondroid.behealthy.utils.asEventFlow
import com.moondroid.behealthy.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appVersionUseCase: AppVersionUseCase,
    private val profileUseCase: ProfileUseCase,
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
                }.onFail {
                    //TODO Handle Api Error
                }.onError {
                    it.logException()
                }
            }
        }
    }

    fun checkUserInfo() {
        viewModelScope.launch {
            profileUseCase().collect {
                it?.let {
                    event(SplashEvent.Home(it))
                } ?: run {
                    event(SplashEvent.Sign)
                }
            }
        }
    }

    private fun event(event: SplashEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class SplashEvent {
        data class Version(val code: Int) : SplashEvent()
        data class Home(val profile: Profile) : SplashEvent()
        object Sign : SplashEvent()
    }
}