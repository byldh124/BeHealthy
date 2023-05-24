package com.moondroid.behealthy.view.ui.sign

import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(): BaseViewModel() {

    fun sign(id: String, name: String, thumb: String, type : Int) {
        debug("ID : $id, NAME: $name, THUMB : $thumb, type: $type")
    }
}