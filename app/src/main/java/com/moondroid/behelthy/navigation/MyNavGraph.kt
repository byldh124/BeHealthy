package com.moondroid.behelthy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moondroid.behelthy.ui.screen.home.HomeScreen
import com.moondroid.behelthy.ui.screen.sign.SignScreen
import com.moondroid.behelthy.ui.screen.splash.SplashScreen

@Composable
fun MyNavGraph(
    navController: NavHostController = rememberNavController(),
    navigationAction: MyNavigationAction,
    startDestination: String = MyDestination.SPLASH_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MyDestination.SPLASH_ROUTE) { _ ->
            //Main 에서 viewModel 생성 후 전달
            SplashScreen(
                navigationAction
            )
        }

        composable(route = MyDestination.SIGN_ROUTE) { backStackEntry ->
            //Note 에서 viewModel 생성
            //val noteViewModel = hiltViewModel<NoteViewModel>()

            SignScreen(
                navController = navController
            )
        }

        composable(route = MyDestination.HOME_ROUTE) { navBackStackEntry ->
            HomeScreen(navController = navController)
        }
    }
}