package com.moondroid.behealthy.view.ui.sign

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.databinding.FragmentSignBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

class SignFragment : BaseFragment(R.layout.fragment_sign) {
    private val binding by viewBinding(FragmentSignBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = binding.icGoogle.getChildAt(0) as TextView
        textView.text = "구글 로그인"

        binding.apply {
            icKakao.setOnClickListener { getKakaoAccount() }
            icGoogle.setOnClickListener { getGoogleAccount() }
            tvGuest.setOnClickListener { startWithGuest() }
        }
    }

    private fun getKakaoAccount() {

    }

    private fun getGoogleAccount() {

    }

    private fun startWithGuest() {

    }
}