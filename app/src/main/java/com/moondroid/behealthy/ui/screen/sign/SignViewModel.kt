package com.moondroid.behealthy.ui.screen.sign

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.moondroid.behealthy.common.Extensions
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.common.UserType
import com.moondroid.behealthy.domain.model.status.onError
import com.moondroid.behealthy.domain.model.status.onFail
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.profile.SignUseCase
import com.moondroid.behealthy.domain.usecase.profile.UpdateTokenUseCase
import com.moondroid.behealthy.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val signUseCase: SignUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
) : BaseViewModel() {

    private val _signComplete = mutableStateOf(false)
    val signComplete get() = _signComplete

    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Extensions.debug("카카오톡 로그인 실패 $error")
        } else if (token != null) {
            requestSign()
        }
    }

    fun getKakaoAccount(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context = context)) {
            UserApiClient.instance.loginWithKakaoTalk(context = context) { token, error ->
                if (error != null) {
                    Extensions.debug("카카오톡으로 로그인 실패 $error")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context = context, callback = kakaoCallback)
                } else {
                    if (token != null) {
                        requestSign()
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoCallback)
        }
    }

    private fun requestSign() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                error.logException()
            } else {
                user?.let {
                    val id = it.id?.toString() ?: throw IllegalStateException("ID must not be null")
                    val name = it.kakaoAccount?.profile?.nickname ?: ""
                    val thumb = it.kakaoAccount?.profile?.profileImageUrl ?: ""
                    sign(id, name, thumb, UserType.KAKAO)
                }
            }
        }
    }

    fun sign(id: String = "", name: String = "", thumb: String = "", userType: UserType) {
        CoroutineScope(Dispatchers.Main).launch {
            signUseCase(id, name, thumb, userType.value).collect { result ->
                result.onSuccess {
                    getMsgToken(it.id)
                }.onFail {

                }.onError {
                    it.logException()
                }
            }
        }
    }

    /**
     * FCM 토큰 생성
     * [토큰 생성되지 않은 경우에도 정상처리]
     */
    private fun getMsgToken(id: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                _signComplete.value = true
            }

            val token = task.result
            viewModelScope.launch {
                updateTokenUseCase(id, token).collect {
                    _signComplete.value = true
                }
            }
        }
    }
}