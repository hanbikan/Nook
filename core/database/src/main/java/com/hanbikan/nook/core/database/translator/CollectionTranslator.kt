package com.hanbikan.nook.core.database.translator

import com.hanbikan.nook.core.database.entity.FishEntity
import com.hanbikan.nook.core.domain.model.Fish

fun FishEntity.toDomain(): Fish {
    return Fish(
        id = id,
        userId = userId,
        name = name,
        number = number,
        imageUrl = imageUrl,
        timesByMonth = timesByMonth,
        isCollected = isCollected,
        location = location
    )
}

fun Fish.toData(): FishEntity {
    return FishEntity(
        id = id,
        userId = userId,
        name = name,
        number = number,
        imageUrl = imageUrl,
        timesByMonth = timesByMonth,
        isCollected = isCollected,
        location = location,
    )
}