package com.hanbikan.nook.core.database.translator

import com.hanbikan.nook.core.database.entity.UserEntity
import com.hanbikan.nook.core.domain.model.User

fun UserEntity.toDomain(): User {
    return User(id, name, islandName)
}

fun User.toData(): UserEntity {
    return UserEntity(id, name, islandName)
}