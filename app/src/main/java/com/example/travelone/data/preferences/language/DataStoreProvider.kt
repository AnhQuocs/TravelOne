package com.example.travelone.data.preferences.language

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.languageDataStore by preferencesDataStore(name = "settings")