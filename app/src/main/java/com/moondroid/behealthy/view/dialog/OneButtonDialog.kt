package com.moondroid.behealthy.view.dialog

import android.content.Context
import android.os.Bundle
import com.moondroid.behealthy.databinding.DialogOneButtonBinding
import com.moondroid.behealthy.view.base.BaseDialog

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