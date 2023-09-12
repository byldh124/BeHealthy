package com.moondroid.legacy.view.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moondroid.legacy.R
import com.moondroid.legacy.databinding.FragmentBoardBinding
import com.moondroid.legacy.view.base.BaseFragment

class BoardFragment : BaseFragment(R.layout.fragment_board) {
    private var _binding: FragmentBoardBinding? = null
    private val binding: FragmentBoardBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }
}