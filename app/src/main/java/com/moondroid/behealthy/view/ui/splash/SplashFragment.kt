package com.moondroid.behealthy.view.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.moondroid.behealthy.BuildConfig
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.common.Extensions.repeatOnStarted
import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.databinding.FragmentSplashBinding
import com.moondroid.behealthy.domain.model.BaseResponse
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.application.AppVersionUseCase
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment
import com.moondroid.behealthy.view.ui.splash.SplashViewModel.SplashEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()
    private val binding by viewBinding(FragmentSplashBinding::bind)

    @Inject
    lateinit var checkAppVersion: AppVersionUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repeatOnStarted {
            viewModel.eventFLow.collect {
                handleEvent(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkAppVersion(
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME,
            mContext.packageName
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
        updateIntent.data = Uri.parse("market://details?id=${mContext.packageName}")
        mContext.startActivity(updateIntent)
    } catch (e: Exception) {
        e.logException()
    }

    private fun handleEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Version -> checkAppVersion(event.code)
            SplashEvent.Home -> TODO()
            SplashEvent.Sign -> findNavController().navigate(R.id.splashToSign)
        }
    }
}