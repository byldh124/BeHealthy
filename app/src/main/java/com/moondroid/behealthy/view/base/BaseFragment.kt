package com.moondroid.behealthy.view.base

import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    private var _mContext: Context? = null
    protected val mContext get() = _mContext!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mContext = context
    }
}