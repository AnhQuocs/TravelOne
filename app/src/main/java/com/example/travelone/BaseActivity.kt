package com.example.travelone

import android.content.Context
import androidx.activity.ComponentActivity
import com.example.travelone.data.preferences.language.LanguagePreferenceManager
import com.example.travelone.domain.model.language.AppLanguage
import com.example.travelone.utils.LanguageManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

open class BaseComponentActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val updatedContext = runBlocking {
            val manager = LanguagePreferenceManager(newBase)
            val lang = manager.languageFlow.firstOrNull() ?: AppLanguage.ENGLISH
            LanguageManager.setAppLocale(newBase, lang)
        }
        super.attachBaseContext(updatedContext)
    }
}