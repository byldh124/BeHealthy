package com.moondroid.legacy.view.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.moondroid.legacy.common.Extensions.exitApp
import com.moondroid.legacy.common.Extensions.logException
import com.moondroid.legacy.common.Extensions.repeatOnStarted
import com.moondroid.legacy.common.ResponseCode
import com.moondroid.legacy.domain.usecase.application.AppVersionUseCase
import com.moondroid.legacy.BHApp
import com.moondroid.legacy.BuildConfig
import com.moondroid.legacy.R
import com.moondroid.legacy.databinding.ActivitySplashBinding
import com.moondroid.legacy.utils.viewBinding
import com.moondroid.legacy.view.base.BaseActivity
import com.moondroid.legacy.view.ui.sign.SignActivity
import com.moondroid.legacy.view.ui.splash.SplashViewModel.SplashEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var checkAppVersion: AppVersionUseCase

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFLow.collect {
                handleEvent(it)
            }
        }
        val resId =
            resources.getIdentifier(String.format("box%02d", (Random.nextInt(29) + 1)), "color", packageName)
        binding.logo.setTextColor(ContextCompat.getColor(this, resId))

        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_logo)
        binding.logo.startAnimation(anim)

        viewModel.checkAppVersion(
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME,
            packageName
        )
    }

    private fun checkAppVersion(code: Int) {
        when (code) {
            ResponseCode.FAIL -> showMessage(R.string.desc_sever_error)
            ResponseCode.INACTIVE -> requestUpdate()
            ResponseCode.NOT_EXIST -> showMessage("버전 정보가 존재하지 않습니다.") { exitApp() }
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