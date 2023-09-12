package com.moondroid.legacy.ui.screen.sign

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.moondroid.legacy.navigation.MyDestination.HOME_ROUTE

@Composable
fun SignScreen(
    navController: NavController,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { navController.navigate(HOME_ROUTE) }) {
            Text(text = "홈으로")
        }
    }
}