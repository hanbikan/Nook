package com.hanbikan.nooknook.core.database.translator

import com.hanbikan.nooknook.core.database.entity.UserEntity
import com.hanbikan.nooknook.core.domain.model.User

fun UserEntity.toDomain(): User {
    return User(id, name, islandName)
}

fun User.toData(): UserEntity {
    return UserEntity(id, name, islandName)
}