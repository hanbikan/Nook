package com.hanbikan.nook.core.database.translator

import com.hanbikan.nook.core.database.entity.BugEntity
import com.hanbikan.nook.core.database.entity.FishEntity
import com.hanbikan.nook.core.domain.model.Bug
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

fun BugEntity.toDomain(): Bug {
    return Bug(
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

fun Bug.toData(): BugEntity {
    return BugEntity(
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