package com.moondroid.behealthy.view.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.moondroid.behealthy.BuildConfig
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.common.Extensions.logException
import com.moondroid.behealthy.common.ResponseCode
import com.moondroid.behealthy.databinding.FragmentSplashBinding
import com.moondroid.behealthy.domain.model.BaseResponse
import com.moondroid.behealthy.domain.model.status.onSuccess
import com.moondroid.behealthy.domain.usecase.application.AppVersionUseCase
import com.moondroid.behealthy.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var checkAppVersion: AppVersionUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val res = checkAppVersion(
                BuildConfig.VERSION_CODE,
                BuildConfig.VERSION_NAME,
                mContext.packageName
            )
            res.collect {result ->
                result.onSuccess {
                    checkAppVersion(it.code)
                }
            }
        }
    }

    private fun checkAppVersion(code: Int) {
        when(code) {
            ResponseCode.SUCCESS -> checkUserInfo()
            ResponseCode.FAIL -> {
                //TODO FAIL
            }
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

    private fun checkUserInfo() {
        //TODO Room 에 저장된 유저 정보 수집
        debug("checkUseInfo api call")
    }
}