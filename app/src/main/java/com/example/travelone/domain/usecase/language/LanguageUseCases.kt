package com.example.travelone.domain.usecase.language

import javax.inject.Inject

data class LanguageUseCases @Inject constructor(
    val getLanguage: GetLanguageUseCase,
    val updateLanguage: UpdateLanguageUseCase
)