package com.example.travelone.data.mapper.auth

import com.example.travelone.data.model.auth.UserDto
import com.example.travelone.domain.model.auth.User

fun UserDto.toDomain(): User {
    return User(uid, email, username)
}

fun User.toDto(): UserDto {
    return UserDto(uid, email, username)
}