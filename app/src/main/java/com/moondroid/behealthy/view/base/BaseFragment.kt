package com.moondroid.behealthy.view.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

open class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    private var _mContext: Context? = null
    protected val mContext get() = _mContext!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mContext = context
    }

    protected fun showMessage(@StringRes res: Int, onClick: () -> Unit = {}) {
        showMessage(getString(res), onClick)
    }

    protected fun showMessage(message: String, onClick: () -> Unit = {}) {

    }
}