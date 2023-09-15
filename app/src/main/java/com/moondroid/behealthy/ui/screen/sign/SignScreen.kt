package com.moondroid.behealthy.ui.screen.sign

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.UserType
import com.moondroid.behealthy.navigation.MyNavigationAction
import com.moondroid.behealthy.ui.theme.Blue01
import com.moondroid.behealthy.ui.theme.NANUM_EB

@Composable
fun SignScreen(
    navigationAction: MyNavigationAction,
) {
    val viewModel = hiltViewModel<SignViewModel>()

    LaunchedEffect(key1 = viewModel.signComplete.value) {
        if (viewModel.signComplete.value) {
            navigationAction.toHomeFromSign()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignHeader()
        LoginButton(viewModel)
        GuestButton(viewModel)
    }
}

@Composable
fun SignHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Blue01)
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 40.sp,
            fontFamily = NANUM_EB,
            color = Color.White
        )

        Text(
            text = stringResource(id = R.string.sub_title),
            fontSize = 20.sp,
            fontFamily = NANUM_EB,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoginButton(viewModel: SignViewModel) {

    val context = LocalContext.current

    GlideImage(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(40.dp)
            .clickable {
                viewModel.getKakaoAccount(context = context)
            },
        model = R.drawable.img_kakao_login,
        contentDescription = "kakao",
        contentScale = ContentScale.Crop
    )
}

@Composable
fun GuestButton(viewModel: SignViewModel) {
    Text(
        text = stringResource(id = R.string.desc_sign_by_guest),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(40.dp)
            .background(color = Blue01)
            .clickable {
                viewModel.sign(userType = UserType.GUEST)
            }
    )
}
