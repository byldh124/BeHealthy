package com.moondroid.behealthy.ui.screen.splash


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moondroid.behealthy.navigation.MyNavigationAction

@Composable
fun SplashScreen(
    navigationAction: MyNavigationAction,
) {
    MyBox(navigationAction)
    val viewModel :SplashViewModel = hiltViewModel()
}

@Composable
fun MyBox(navigationAction: MyNavigationAction) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { navigationAction.toSign() }) {
            Text(text = "로그인")
        }
    }
}