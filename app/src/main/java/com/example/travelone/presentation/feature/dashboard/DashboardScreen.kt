package com.example.travelone.presentation.feature.dashboard

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.travelone.R
import com.example.travelone.presentation.components.AppButton
import com.example.travelone.presentation.feature.auth.ui.LoginSection
import com.example.travelone.presentation.feature.auth.ui.SignUpSection
import com.example.travelone.presentation.feature.auth.viewmodel.AuthActionType
import com.example.travelone.presentation.feature.auth.viewmodel.AuthViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.MintGreen
import com.example.travelone.ui.theme.SoftGreen
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DashboardScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val authState by authViewModel.authState.collectAsState()
    val lastAction by authViewModel.lastAuthAction.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    val usernameError by authViewModel.usernameError.collectAsState()
    val emailError by authViewModel.emailError.collectAsState()
    val passwordError by authViewModel.passwordError.collectAsState()

    val wavyFont = FontFamily(Font(R.font.wavy_font))

    var isShowForm by remember { mutableStateOf(false) }
    var isLogin by remember { mutableStateOf(true) }

    var offsetY by remember { mutableStateOf(0f) }
    val dragThreshold = 200f
    val context = LocalContext.current

    LaunchedEffect(authState) {
        authState?.onSuccess {
            val message = when (lastAction) {
                AuthActionType.LOGIN -> context.getString(R.string.login_success)
                AuthActionType.SIGN_UP -> context.getString(R.string.sign_up_success)
                else -> null
            }
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
            authViewModel.clearAuthState()
        }

        authState?.onFailure { throwable ->
            val message = when {
                throwable is FirebaseAuthUserCollisionException -> context.getString(R.string.email_already_exists)
                lastAction == AuthActionType.LOGIN -> context.getString(R.string.login_failed)
                lastAction == AuthActionType.SIGN_UP -> context.getString(R.string.sign_up_failed)
                else -> context.getString(R.string.unknown_error)
            }

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            authViewModel.clearAuthState()
        }
    }

    LaunchedEffect(isLoading, authState, lastAction) {
        if (!isLoading && authState?.isSuccess == true && lastAction == AuthActionType.SIGN_UP) {
            isLogin = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dashboard_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.3f))
        )

        Text(
            text = stringResource(id = R.string.app_name),
            fontFamily = wavyFont,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            modifier = Modifier
                .padding(top = Dimens.DashboardPadding)
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.dashboard_dont_wait),
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )

            Text(
                text = stringResource(id = R.string.dashboard_sub_dont_wait),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        if (!isShowForm) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(Dimens.ExtraLargePadding)
                    .padding(bottom = Dimens.LargePadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppButton(
                    onClick = {
                        isLogin = true
                        isShowForm = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MintGreen
                    ),
                    shape = RoundedCornerShape(AppShape.ExtraExtraLargeShape),
                    text = stringResource(id = R.string.login_button)
                )

                Spacer(modifier = Modifier.height(AppSpacing.Large))

                OutlinedButton(
                    onClick = {
                        isLogin = false
                        isShowForm = true
                    },
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.DefaultComponentHeight)
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up_prompt),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }
        }

        LaunchedEffect(isShowForm) {
            if (isShowForm) offsetY = 0f
        }

        AnimatedVisibility(
            visible = isShowForm,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300)
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(0, offsetY.roundToInt()) }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { _, dragAmount ->
                                if (dragAmount > 0) {
                                    offsetY += dragAmount
                                }
                            },
                            onDragEnd = {
                                if (offsetY > dragThreshold) {
                                    isShowForm = false
                                } else {
                                    offsetY = 0f
                                }
                            }
                        )
                    }
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = AppShape.SuperRoundedShape, topEnd = AppShape.SuperRoundedShape))
                    .background(color = SoftGreen)
                    .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 150,
                        easing = LinearEasing
                    ))
                    .padding(Dimens.LargePadding)
            ) {
                AnimatedContent(
                    targetState = isLogin,
                    transitionSpec = {
                        if (targetState) {
                            slideInHorizontally(
                                animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
                                initialOffsetX = { fullWidth -> fullWidth }
                            ) + fadeIn(animationSpec = tween(100)) with

                                    slideOutHorizontally(
                                        animationSpec = tween(100, easing = FastOutSlowInEasing),
                                        targetOffsetX = { fullWidth -> -fullWidth / 4 }
                                    ) + fadeOut(animationSpec = tween(100))
                        } else {
                            slideInHorizontally(
                                animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing),
                                initialOffsetX = { fullWidth -> -fullWidth }
                            ) + fadeIn(animationSpec = tween(100)) with

                                    slideOutHorizontally(
                                        animationSpec = tween(100, easing = FastOutSlowInEasing),
                                        targetOffsetX = { fullWidth -> fullWidth / 4 }
                                    ) + fadeOut(animationSpec = tween(100))
                        }
                    }
                ) { target ->
                    if (target) {
                        LoginSection(
                            onLogin = { email, pass ->
                                authViewModel.login(email, pass)
                            },
                            onSwitch = { isLogin = false },
                            emailError = emailError,
                            clearEmailError = authViewModel::clearEmailError,
                            passwordError = passwordError,
                            clearPasswordError = authViewModel::clearPasswordError,
                            isLoggedIn = isLoggedIn,
                            navHostController = navHostController
                        )
                    } else {
                        SignUpSection(
                            onSignUp = { username, email, pass ->
                                authViewModel.signUp(username, email, pass)
                            },
                            onSwitch = { isLogin = true },
                            usernameError = usernameError,
                            clearUsernameError = authViewModel::clearUsernameError,
                            emailError = emailError,
                            clearEmailError = authViewModel::clearEmailError,
                            passwordError = passwordError,
                            clearPasswordError = authViewModel::clearPasswordError
                        )
                    }
                }
            }
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        Log.d("AuthViewModel", "Loading State: $isLoading")
    }
}