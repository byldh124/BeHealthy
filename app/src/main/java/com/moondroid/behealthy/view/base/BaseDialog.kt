package com.moondroid.behealthy.view.base

import android.app.Dialog
import android.content.Context
import com.moondroid.behealthy.R

open class BaseDialog(context: Context) : Dialog(context, R.style.DialogTheme) {
    fun exit() {
        if (isShowing) super.cancel()
    }
}