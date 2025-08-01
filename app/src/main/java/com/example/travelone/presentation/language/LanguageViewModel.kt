package com.example.travelone.presentation.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelone.domain.model.language.AppLanguage
import com.example.travelone.domain.usecase.language.GetLanguageUseCase
import com.example.travelone.domain.usecase.language.UpdateLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase,
    private val updateLanguageUseCase: UpdateLanguageUseCase
): ViewModel() {

    private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    init {
        viewModelScope.launch {
            getLanguageUseCase().collect {
                _currentLanguage.value = it
            }
        }
    }

    fun changeLanguage(language: AppLanguage) {
        viewModelScope.launch {
            updateLanguageUseCase(language)
        }
    }
}