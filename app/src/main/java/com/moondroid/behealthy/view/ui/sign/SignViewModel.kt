package com.moondroid.behealthy.view.ui.sign

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.common.Extensions.toast
import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.domain.model.Profile
import com.moondroid.behealthy.domain.model.status.onError
import com.moondroid.behealthy.domain.model.status.onFail
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.profile.SignUseCase
import com.moondroid.behealthy.domain.usecase.profile.UpdateTokenUseCase
import com.moondroid.behealthy.utils.EventFlow
import com.moondroid.behealthy.utils.MutableEventFlow
import com.moondroid.behealthy.utils.asEventFlow
import com.moondroid.behealthy.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val signUseCase: SignUseCase,
    private val updateTokenUseCase: UpdateTokenUseCase,
) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<SignEvent>()
    val eventFlow = _eventFlow.asEventFlow()

    fun sign(id: String, name: String, thumb: String, type: Int) {
        viewModelScope.launch {
            signUseCase(id, name, thumb, type).collect { result ->
                result.onSuccess {
                    getMsgToken(it)
                }.onFail {
                    debug("서버에 문제 있음")
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
    private fun getMsgToken(profile: Profile) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                event(SignEvent.SignDone(profile))
                return@OnCompleteListener
            }

            val token = task.result
            viewModelScope.launch {
                updateTokenUseCase(profile.id, token).collect {
                    event(SignEvent.SignDone(profile))
                }
            }
        })
    }

    private fun event(event: SignEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class SignEvent {
        data class SignDone(val profile: Profile) : SignEvent()
    }
}
