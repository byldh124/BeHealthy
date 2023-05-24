package com.moondroid.behealthy.view.ui.board

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.moondroid.behealthy.R
import com.moondroid.behealthy.databinding.FragmentBoardListBinding
import com.moondroid.behealthy.utils.viewBinding
import com.moondroid.behealthy.view.base.BaseFragment
import com.moondroid.behealthy.view.ui.MainActivity

class BoardListFragment: BaseFragment(R.layout.fragment_board_list) {
    private val binding by viewBinding(FragmentBoardListBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (mContext as MainActivity).onBackPressedDispatcher.addCallback(this) {
            (mContext as MainActivity).mFinish()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnWrite.setOnClickListener {
            findNavController().navigate(BoardListFragmentDirections.actionBoardListFragmentToBoardWriteFragment())
        }

        binding.btnDetail.setOnClickListener {
            findNavController().navigate(BoardListFragmentDirections.actionBoardListFragmentToBoardDetailFragment())
        }
    }
}