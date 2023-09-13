package com.moondroid.behealthy.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.domain.model.status.onError
import com.moondroid.behealthy.domain.model.status.onFail
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.application.AppVersionUseCase
import com.moondroid.behealthy.domain.usecase.profile.GetProfileUseCase
import com.moondroid.behealthy.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface SplashUiState {
    data object Ready : SplashUiState
    data object Update : SplashUiState
    data class Fail(val message: String) : SplashUiState
    data object Sign : SplashUiState
    data object Home : SplashUiState
}

data class SplashScreenUiState(
    val state: SplashUiState,
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appVersionUseCase: AppVersionUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(SplashScreenUiState(SplashUiState.Ready))
    val uiState = _uiState.asStateFlow()

    fun checkAppVersion(versionCode: Int, versionName: String, packageName: String) {
        viewModelScope.launch {
            appVersionUseCase(versionCode, versionName, packageName).collect { result ->
                result.onSuccess {
                    checkUserInfo()
                }.onFail {
                    val uiState = when (it) {
                        ResponseCode.NOT_EXIST -> SplashUiState.Fail("해당 버전이 존재하지 않습니다. [$versionName]")
                        ResponseCode.INACTIVE -> SplashUiState.Update
                        else -> SplashUiState.Fail("서버 에러 - 고객센터에 문의해주세요.")
                    }
                    _uiState.value = SplashScreenUiState(uiState)

                }.onError {
                    _uiState.value =
                        SplashScreenUiState(SplashUiState.Fail("Error\n${it.javaClass.simpleName}"))
                }
            }
        }
    }

    private fun checkUserInfo() {
        viewModelScope.launch {
            getProfileUseCase().collect {
                delay(1500)
                it?.let {
                    _uiState.value = SplashScreenUiState(SplashUiState.Home)
                } ?: run {
                    _uiState.value = SplashScreenUiState(SplashUiState.Sign)
                }
            }
        }
    }
}