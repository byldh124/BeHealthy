package com.moondroid.legacy.view.ui.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.legacy.common.Extensions.logException
import com.moondroid.legacy.domain.model.Profile
import com.moondroid.legacy.domain.model.status.onError
import com.moondroid.legacy.domain.model.status.onFail
import com.moondroid.legacy.domain.model.status.onSuccess
import com.moondroid.legacy.domain.usecase.application.AppVersionUseCase
import com.moondroid.legacy.domain.usecase.profile.GetProfileUseCase
import com.moondroid.legacy.utils.MutableEventFlow
import com.moondroid.legacy.utils.asEventFlow
import com.moondroid.legacy.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appVersionUseCase: AppVersionUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {
    private val _eventFlow = MutableEventFlow<SplashEvent>()
    val eventFLow = _eventFlow.asEventFlow()

    fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ) {
        viewModelScope.launch {
            delay(1500)
            appVersionUseCase(versionCode, versionName, packageName).collect { result ->
                result.onSuccess {
                    checkUserInfo()
                }.onFail {
                    event(SplashEvent.Version(it))
                }.onError {
                    it.logException()
                }
            }
        }
    }

    private fun checkUserInfo() {
        viewModelScope.launch {
            getProfileUseCase().collect {
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
        data object Sign : SplashEvent()
    }
}