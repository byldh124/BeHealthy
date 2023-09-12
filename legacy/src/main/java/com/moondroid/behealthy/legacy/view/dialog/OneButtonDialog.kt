package com.moondroid.behealthy.legacy.view.dialog

import android.content.Context
import android.os.Bundle
import com.moondroid.behealthy.legacy.databinding.DialogOneButtonBinding
import com.moondroid.behealthy.legacy.view.base.BaseDialog

class OneButtonDialog(context: Context, var message: String, var onClick: () -> Unit) :
    BaseDialog(context) {

    lateinit var binding: DialogOneButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogOneButtonBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            cancel()
        }
    }

    override fun show() {
        super.show()
        binding.tvMessage.text = message
    }

    override fun cancel() {
        super.cancel()
        onClick()
    }
}