package com.moondroid.behealthy.ui.screen.splash

import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {
    init {
        debug("init")
    }
}