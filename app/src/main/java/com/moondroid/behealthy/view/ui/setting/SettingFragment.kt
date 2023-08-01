package com.moondroid.behealthy.view.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moondroid.behealthy.R
import com.moondroid.behealthy.databinding.FragmentSettingBinding
import com.moondroid.behealthy.view.base.BaseFragment

class SettingFragment : BaseFragment(R.layout.fragment_setting){
    private var _binding : FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }
}