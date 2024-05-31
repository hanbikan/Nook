package com.hanbikan.nook.core.database.translator

import com.hanbikan.nook.core.database.entity.BugEntity
import com.hanbikan.nook.core.database.entity.FishEntity
import com.hanbikan.nook.core.database.entity.SeaCreatureEntity
import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature

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

fun SeaCreatureEntity.toDomain(): SeaCreature {
    return SeaCreature(
        id = id,
        userId = userId,
        name = name,
        number = number,
        imageUrl = imageUrl,
        timesByMonth = timesByMonth,
        isCollected = isCollected,
    )
}

fun SeaCreature.toData(): SeaCreatureEntity {
    return SeaCreatureEntity(
        id = id,
        userId = userId,
        name = name,
        number = number,
        imageUrl = imageUrl,
        timesByMonth = timesByMonth,
        isCollected = isCollected,
    )
}