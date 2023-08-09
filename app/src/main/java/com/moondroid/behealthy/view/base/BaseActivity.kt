package com.moondroid.behealthy.view.base

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.moondroid.behealthy.domain.model.Item
import com.moondroid.behealthy.view.dialog.OneButtonDialog
import com.moondroid.behealthy.view.ui.home.HomeActivity

open class BaseActivity : AppCompatActivity() {

    private var oneButtonDialog: OneButtonDialog? = null
    protected val mContext by lazy { this }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseDialog()
    }

    private fun releaseDialog() {
        oneButtonDialog?.exit()
    }

    fun showMessage(@StringRes res: Int, onClick: () -> Unit = {}) {
        showMessage(getString(res), onClick)
    }

    fun showMessage(message: String, onClick: () -> Unit = {}) {
        oneButtonDialog?.let {
            it.message = message
            it.onClick = onClick
            it.show()
        } ?: run {
            oneButtonDialog = OneButtonDialog(this, message, onClick).apply {
                show()
            }
        }
    }

    protected fun toHome() {
        startActivity(Intent(mContext, HomeActivity::class.java))
        finishAffinity()
    }
}

fun List<Item>.containType(vararg itemType: Int): Boolean {
    var temp = 0
    forEach {
        if (itemType.contains(it.type)) temp++
    }
    return temp == itemType.size
}