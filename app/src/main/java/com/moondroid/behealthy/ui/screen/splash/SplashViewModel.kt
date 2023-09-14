package com.moondroid.behealthy.ui.screen.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface SplashAction {
    data object Ready : SplashAction
    data object Update : SplashAction
    data class Fail(val message: String) : SplashAction
    data object Sign : SplashAction
    data object Home : SplashAction
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appVersionUseCase: AppVersionUseCase,
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel() {

    private val _action = mutableStateOf<SplashAction>(SplashAction.Ready)
    val action: State<SplashAction> get() = _action

    fun checkAppVersion(versionCode: Int, versionName: String, packageName: String) {
        viewModelScope.launch {
            appVersionUseCase(versionCode, versionName, packageName).collect { result ->
                result.onSuccess {
                    checkUserInfo()
                }.onFail {
                    val uiState = when (it) {
                        ResponseCode.NOT_EXIST -> SplashAction.Fail("해당 버전이 존재하지 않습니다. [$versionName]")
                        ResponseCode.INACTIVE -> SplashAction.Update
                        else -> SplashAction.Fail("서버 에러 - 고객센터에 문의해주세요.")
                    }
                    _action.value = uiState

                }.onError {
                    _action.value = SplashAction.Fail("Error\n${it.javaClass.simpleName}")
                }
            }
        }
    }

    private fun checkUserInfo() {
        viewModelScope.launch {
            getProfileUseCase().collect {
                delay(1500)
                it?.let {
                    _action.value = SplashAction.Home
                } ?: run {
                    _action.value = SplashAction.Sign
                }
            }
        }
    }
}