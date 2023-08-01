package com.moondroid.behealthy.view.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.moondroid.behealthy.BHApp
import com.moondroid.behealthy.BuildConfig
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.common.Extensions.repeatOnStarted
import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.domain.usecase.application.AppVersionUseCase
import com.moondroid.behealthy.view.base.BaseActivity
import com.moondroid.behealthy.view.ui.home.HomeActivity
import com.moondroid.behealthy.view.ui.sign.SignActivity
import com.moondroid.behealthy.view.ui.splash.SplashViewModel.SplashEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity(R.layout.activity_splash) {
    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var checkAppVersion: AppVersionUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFLow.collect {
                handleEvent(it)
            }
        }

        viewModel.checkAppVersion(
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME,
            packageName
        )
    }

    private fun checkAppVersion(code: Int) {
        when (code) {
            ResponseCode.SUCCESS -> viewModel.checkUserInfo()
            ResponseCode.FAIL -> showMessage(R.string.desc_sever_error)
            ResponseCode.INACTIVE -> requestUpdate()
        }
    }

    private fun requestUpdate() = try {
        val updateIntent = Intent(Intent.ACTION_VIEW)
        updateIntent.data = Uri.parse("market://details?id=$packageName")
        startActivity(updateIntent)
    } catch (e: Exception) {
        e.logException()
    }

    private fun handleEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Version -> checkAppVersion(event.code)
            is SplashEvent.Home -> {
                BHApp.profile = event.profile
                toHome()
            }

            SplashEvent.Sign -> {
                startActivity(Intent(this, SignActivity::class.java))
                finish()
            }
        }
    }
}