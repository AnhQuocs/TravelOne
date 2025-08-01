package com.example.travelone.presentation.feature.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.travelone.R
import com.example.travelone.presentation.components.AppButton
import com.example.travelone.presentation.components.NormalOutlinedTextField
import com.example.travelone.presentation.components.PasswordOutlinedTextField
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.ConfirmButtonColor
import com.example.travelone.ui.theme.Dimens

@Composable
fun SignUpSection(
    onSwitch: () -> Unit,
    onSignUp: (String, String, String) -> Unit,
    usernameError: String?,
    clearUsernameError: () -> Unit,
    emailError: String?,
    clearEmailError: () -> Unit,
    passwordError: String?,
    clearPasswordError: () -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val usernameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    Column {
        Text(
            text = stringResource(id = R.string.sign_up_title),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.SmallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NormalOutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    clearUsernameError()
                },
                label = "Username",
                leadingIcon = Icons.Default.Person,
                focusRequester = usernameFocusRequester,
                isError = usernameError != null,
                errorMessage = usernameError,
                imeAction = ImeAction.Next,
                onImeAction = {
                    emailFocusRequester.requestFocus()
                }
            )

            Spacer(modifier = Modifier.height(AppSpacing.Large))

            NormalOutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    clearEmailError()
                },
                label = "Email",
                leadingIcon = Icons.Default.Email,
                focusRequester = emailFocusRequester,
                isError = emailError != null,
                errorMessage = emailError,
                imeAction = ImeAction.Next,
                onImeAction = {
                    passwordFocusRequester.requestFocus()
                }
            )

            Spacer(modifier = Modifier.height(AppSpacing.Large))

            PasswordOutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    clearPasswordError()
                },
                label = "Password",
                isError = passwordError != null,
                focusRequester = passwordFocusRequester,
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(AppSpacing.Jumbo))

            AppButton(
                onClick = {
                    onSignUp(email, password, username)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ConfirmButtonColor
                ),
                shape = RoundedCornerShape(AppShape.MediumShape),
                text = stringResource(id = R.string.sign_up_button)
            )

            Spacer(modifier = Modifier.height(AppSpacing.Medium))

            TextButton(
                onClick = { onSwitch() }
            ) {
                Text(
                    text = stringResource(id = R.string.login_prompt),
                    color = Color.White
                )
            }
        }
    }
}