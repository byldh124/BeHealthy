package com.moondroid.legacy.navigation

import androidx.navigation.NavHostController
import com.moondroid.legacy.navigation.MyDestination.HOME_ROUTE
import com.moondroid.legacy.navigation.MyDestination.SIGN_ROUTE


object MyDestination {
    const val SPLASH_ROUTE = "splash"
    const val SIGN_ROUTE = "sign"
    const val HOME_ROUTE = "home"
}

class MyNavigationAction(navController: NavHostController) {
    val toSign: () -> Unit = {
        navController.navigate(SIGN_ROUTE)
    }

    val toHome: () -> Unit = {
        navController.navigate(HOME_ROUTE)
    }
}