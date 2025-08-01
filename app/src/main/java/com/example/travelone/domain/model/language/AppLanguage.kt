package com.example.travelone.domain.model.language

enum class AppLanguage(val code: String) {
    ENGLISH("en"),
    VIETNAMESE("vi");

    companion object {
        fun fromCode(code: String): AppLanguage {
            return values().firstOrNull { it.code == code } ?: ENGLISH
        }
    }
}