package com.moondroid.legacy.view.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moondroid.legacy.R
import com.moondroid.legacy.databinding.FragmentSettingBinding
import com.moondroid.legacy.view.base.BaseFragment

class SettingFragment : BaseFragment(R.layout.fragment_setting) {
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }
}