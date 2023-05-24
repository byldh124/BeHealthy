package com.moondroid.behealthy.view.base

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity(@LayoutRes layout: Int) : AppCompatActivity(layout) {

    protected fun showMessage(@StringRes res: Int, onClick: () -> Unit = {}) {
        showMessage(getString(res), onClick)
    }

    protected fun showMessage(message: String, onClick: () -> Unit = {}) {

    }
}