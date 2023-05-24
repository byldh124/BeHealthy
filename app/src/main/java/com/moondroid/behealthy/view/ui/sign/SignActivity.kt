package com.moondroid.behealthy.view.ui.sign

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.databinding.ActivitySignBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseActivity
import com.moondroid.behealthy.view.ui.MainActivity

class SignActivity : BaseActivity(R.layout.activity_sign) {
    private val binding by viewBinding(ActivitySignBinding::inflate)
    private val viewModel: SignViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = binding.icGoogle.getChildAt(0) as TextView
        textView.text = getString(R.string.desc_sign_with_google)

        binding.apply {
            icKakao.setOnClickListener { getKakaoAccount() }
            icGoogle.setOnClickListener { getGoogleAccount() }
            tvGuest.setOnClickListener { startWithGuest() }
        }
    }
    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            debug("카카오톡 로그인 실패 $error")
        } else if (token != null) {
            requestSign()
        }
    }

    private fun getKakaoAccount() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SignActivity)) {
            UserApiClient.instance.loginWithKakaoTalk(this@SignActivity) { token, error ->
                if (error != null) {
                    debug("카카오톡으로 로그인 실패 $error")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this@SignActivity, callback = kakaoCallback)
                } else {
                    if (token != null) {
                        requestSign()
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@SignActivity, callback = kakaoCallback)
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
                    viewModel.sign(id, name, thumb, SIGN_WITH_KAKAO)
                } ?: run {
                    showMessage(getString(R.string.error_kakao_user_info))
                }
            }
        }
    }

    //Google Account Activity Result
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            try {
                if (it.resultCode == RESULT_OK) {
                    val data: Intent? = it.data
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)

                    val id = account.id ?: throw IllegalStateException("ID must not be null")
                    val name = account.displayName ?: ""
                    val thumb = account.photoUrl?.toString() ?: ""

                    viewModel.sign(id, name, thumb, SIGN_WITH_GOOGLE)
                } else {
                    debug("result code : ${it.resultCode}")
                }
            } catch (e: Exception) {
                e.logException()
            }
        }

    private fun getGoogleAccount() {
        try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            val mGoogleSignInClient: GoogleSignInClient =
                GoogleSignIn.getClient(this@SignActivity, gso)

            val singInIntent = mGoogleSignInClient.signInIntent

            resultLauncher.launch(singInIntent)
        } catch (e: Exception) {
            e.logException()
        }
    }

    private fun startWithGuest() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    companion object {
        const val SIGN_WITH_GUEST = 0
        const val SIGN_WITH_KAKAO = 1
        const val SIGN_WITH_GOOGLE = 2
    }
}