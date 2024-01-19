package com.hanbikan.nookie.core.database.translator

import com.hanbikan.nookie.core.database.entity.UserEntity
import com.hanbikan.nookie.core.domain.model.User

fun UserEntity.toDomain(): User {
    return User(id, name, islandName)
}

fun User.toData(): UserEntity {
    return UserEntity(id, name, islandName)
}