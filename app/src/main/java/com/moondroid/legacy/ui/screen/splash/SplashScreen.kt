package com.moondroid.legacy.ui.screen.splash


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.moondroid.legacy.navigation.MyNavigationAction

@Composable
fun SplashScreen(
    navigationAction: MyNavigationAction,
) {
    MyBox(navigationAction)
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