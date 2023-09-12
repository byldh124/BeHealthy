package com.moondroid.behealthy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.moondroid.behealthy.navigation.MyDestination
import com.moondroid.behealthy.navigation.MyNavGraph
import com.moondroid.behealthy.navigation.MyNavigationAction
import com.moondroid.behealthy.ui.theme.BeHealthyTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    BeHealthyTheme {
        val navController = rememberNavController()
        val navigationAction = remember(navController) {
            MyNavigationAction(navController = navController)
        }
        MyNavGraph(
            navController = navController,
            navigationAction = navigationAction,
            startDestination = MyDestination.SPLASH_ROUTE
        )
    }
}