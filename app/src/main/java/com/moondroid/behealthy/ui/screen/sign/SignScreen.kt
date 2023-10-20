package com.moondroid.behealthy.ui.screen.sign

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.moondroid.behealthy.R
import com.moondroid.behealthy.common.UserType
import com.moondroid.behealthy.navigation.MyNavigationAction
import com.moondroid.behealthy.ui.theme.Blue01
import com.moondroid.behealthy.ui.theme.NANUM_EB

@Composable
fun SignScreen(
    navigationAction: MyNavigationAction,
    viewModel: SignViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    LaunchedEffect(key1 = viewModel.signComplete.value) {
        if (viewModel.signComplete.value) {
            navigationAction.toHomeFromSign()
        }
    }

    val googleLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)

            val id = account.id ?: throw IllegalStateException("ID must not be null")
            val name = account.displayName ?: ""
            val thumb = account.photoUrl?.toString() ?: ""

            viewModel.sign(id, name, thumb, UserType.GOOGLE)
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LoginButton(R.drawable.img_kakao_login) {
                viewModel.getKakaoAccount(context)
            }
            LoginButton(model = R.drawable.img_kakao_login) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()

                val mGoogleSignInClient: GoogleSignInClient =
                    GoogleSignIn.getClient(context as ComponentActivity, gso)

                val singInIntent = mGoogleSignInClient.signInIntent

                googleLauncher.launch(singInIntent)
            }
        }
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
fun LoginButton(@DrawableRes model: Int, onButtonClicked: () -> Unit) {
    GlideImage(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(40.dp)
            .clickable {
                onButtonClicked()
            },
        model = model,
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