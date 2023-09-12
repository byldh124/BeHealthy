package com.moondroid.behealthy.legacy.view.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    protected lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}