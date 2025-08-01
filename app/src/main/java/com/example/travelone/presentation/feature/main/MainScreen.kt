package com.example.travelone.presentation.feature.main

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.travelone.R
import com.example.travelone.domain.model.language.AppLanguage
import com.example.travelone.presentation.language.LanguageViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val currentLang by languageViewModel.currentLanguage.collectAsState()

    var lastLang by remember { mutableStateOf(currentLang) }

    // Khi currentLang thay đổi thì recreate
    LaunchedEffect(currentLang) {
        if (currentLang != lastLang) {
            lastLang = currentLang
            activity?.recreate()
        }
    }

    Column(modifier = Modifier.padding(16.dp).padding(top = 48.dp)) {
        Button(onClick = {
            languageViewModel.changeLanguage(AppLanguage.ENGLISH)
        }) {
            Text("English 🇬🇧")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            languageViewModel.changeLanguage(AppLanguage.VIETNAMESE)
        }) {
            Text("Tiếng Việt 🇻🇳")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.welcome))
    }
}