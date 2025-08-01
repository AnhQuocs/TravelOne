package com.example.travelone.domain.usecase.language

import com.example.travelone.data.preferences.language.LanguagePreferenceManager
import com.example.travelone.domain.model.language.AppLanguage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val manager: LanguagePreferenceManager
) {
    operator fun invoke(): Flow<AppLanguage> = manager.languageFlow
}

class UpdateLanguageUseCase @Inject constructor(
    private val manager: LanguagePreferenceManager
) {
    suspend operator fun invoke(language: AppLanguage) = manager.saveLanguage(language)
}