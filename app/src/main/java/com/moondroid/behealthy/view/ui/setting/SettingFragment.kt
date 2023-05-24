package com.moondroid.behealthy.view.ui.setting

import android.os.Bundle
import androidx.activity.addCallback
import com.moondroid.behealthy.R
import com.moondroid.behealthy.view.base.BaseFragment
import com.moondroid.behealthy.view.ui.MainActivity

class SettingFragment: BaseFragment(R.layout.fragment_setting) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (mContext as MainActivity).onBackPressedDispatcher.addCallback(this) {
            (mContext as MainActivity).mFinish()
        }
    }
}