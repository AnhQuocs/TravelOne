package com.example.travelone.data.model.auth

data class UserDto(
    val uid: String = "",
    val email: String ?= null,
    val username: String ?= null
)