package com.moondroid.behealthy.view.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moondroid.behealthy.R
import com.moondroid.behealthy.databinding.FragmentBoardBinding
import com.moondroid.behealthy.view.base.BaseFragment

class BoardFragment : BaseFragment(R.layout.fragment_board){
    private var _binding : FragmentBoardBinding? = null
    private val binding: FragmentBoardBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        return binding.root
    }
}