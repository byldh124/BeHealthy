package com.moondroid.behealthy.view.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.moondroid.behealthy.common.Extensions.debug

open class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    protected lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}