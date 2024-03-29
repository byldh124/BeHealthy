package com.moondroid.behealthy.ui.screen.splash


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moondroid.behealthy.BuildConfig
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.Extensions.debug
import com.moondroid.behealthy.navigation.MyNavigationAction
import com.moondroid.behealthy.ui.theme.BoxColor
import com.moondroid.behealthy.ui.theme.JALNAN

@Composable
fun SplashScreen(
    navigationAction: MyNavigationAction,
) {
    val viewModel: SplashViewModel = hiltViewModel()

    val context = LocalContext.current

    val action = remember {
        viewModel.action
    }

    LaunchedEffect(key1 = context.packageName) {
        viewModel.checkAppVersion(
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME,
            context.packageName
        )
    }

    LaunchedEffect(key1 = action.value) {
        when (val value = action.value) {
            SplashAction.Home -> navigationAction.toHomeFromSplash()
            SplashAction.Sign -> navigationAction.toSignFromSplash()
            SplashAction.Update -> requestUpdate(context)
            is SplashAction.Fail -> {
                debug("fail : ${value.message}")
            }

            SplashAction.Ready -> {}
        }
    }

    val color = remember {
        BoxColor.getRandom().color
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Logo(color = color)
    }
}


@Composable
fun Logo(color: Color) {
    Text(
        text = stringResource(id = R.string.app_name),
        fontSize = 48.sp,
        color = color,
        fontFamily = JALNAN
    )
}

private fun requestUpdate(context: Context) {
    val updateIntent = Intent(Intent.ACTION_VIEW)
    updateIntent.data = Uri.parse("market://details?id=${context.packageName}")
    context.startActivity(updateIntent)
}
