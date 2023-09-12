package com.moondroid.behelthy.navigation

import androidx.navigation.NavHostController
import com.moondroid.behelthy.navigation.MyDestination.HOME_ROUTE
import com.moondroid.behelthy.navigation.MyDestination.SIGN_ROUTE


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