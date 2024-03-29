package com.moondroid.behealthy.navigation

import androidx.navigation.NavHostController
import com.moondroid.behealthy.navigation.MyDestination.HOME_ROUTE
import com.moondroid.behealthy.navigation.MyDestination.SIGN_ROUTE
import com.moondroid.behealthy.navigation.MyDestination.SPLASH_ROUTE


object MyDestination {
    const val SPLASH_ROUTE = "splash"
    const val SIGN_ROUTE = "sign"
    const val HOME_ROUTE = "home"
}

class MyNavigationAction(navController: NavHostController) {
    val toSignFromSplash: () -> Unit = {
        navController.navigate(SIGN_ROUTE) {
            popUpTo(SPLASH_ROUTE) { inclusive = true }
        }
    }

    val toHomeFromSign: () -> Unit = {
        navController.navigate(HOME_ROUTE) {
            popUpTo(SIGN_ROUTE) { inclusive = true }
        }
    }

    val toHomeFromSplash: () -> Unit = {
        navController.navigate(HOME_ROUTE) {
            popUpTo(SPLASH_ROUTE) { inclusive = true }
        }
    }
}