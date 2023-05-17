package com.moondroid.healthy.view.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moondroid.healthy.common.Extensions.log
import com.moondroid.healthy.common.Extensions.logException
import com.moondroid.healthy.common.ResponseCode
import com.moondroid.healthy.databinding.FragmentSplashBinding
import com.moondroid.healthy.domain.model.status.onApiError
import com.moondroid.healthy.domain.model.status.onNetworkError
import com.moondroid.healthy.domain.model.status.onSuccess
import com.moondroid.healthy.domain.usecase.application.AppVersionUseCase
import com.moondroid.healthy.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

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

        CoroutineScope(Dispatchers.IO).launch {
            val res = checkAppVersion(1, "com.sehan.sehanpay")
            withContext(Dispatchers.Main) {
                res.collect { result ->
                    result.onSuccess {
                        log("RESULT : $it")
                        when (it.code) {
                            ResponseCode.SUCCESS -> {
                                //TODO VERSION CHECK 성공
                            }

                            ResponseCode.FAIL -> {
                                // TODO 실패
                            }

                            ResponseCode.INACTIVE -> toUpdate()
                        }
                    }
                }
            }
        }
    }

    private fun toUpdate() = try {
        val updateIntent = Intent(Intent.ACTION_VIEW)
        updateIntent.data = Uri.parse("market://details?id=${mContext.packageName}")
        mContext.startActivity(updateIntent)
    } catch (e: Exception) {
        e.logException()
    }
}