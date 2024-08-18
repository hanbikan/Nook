package com.hanbikan.nook.core.database.translator

import com.hanbikan.nook.core.database.entity.BugEntity
import com.hanbikan.nook.core.database.entity.FishEntity
import com.hanbikan.nook.core.database.entity.SeaCreatureEntity
import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature

fun FishEntity.toDomain(): Fish {
    return Fish(
        userId = userId,
        number = number,
        name = name,
        imageUrl = imageUrl,
        renderUrl = renderUrl,
        isCollected = isCollected,
        rarity = rarity,
        totalCatch = totalCatch,
        sellNook = sellNook,
        tankWidth = tankWidth,
        tankLength = tankLength,
        catchphrases = catchphrases,

        isNorth = isNorth,
        timesByMonthSouth = timesByMonthSouth,
        timesByMonthNorth = timesByMonthNorth,

        location = location,

        shadowSize = shadowSize,
    )
}

fun Fish.toData(): FishEntity {
    return FishEntity(
        userId = userId,
        number = number,
        name = name,
        imageUrl = imageUrl,
        renderUrl = renderUrl,
        isCollected = isCollected,
        rarity = rarity,
        totalCatch = totalCatch,
        sellNook = sellNook,
        tankWidth = tankWidth,
        tankLength = tankLength,
        catchphrases = catchphrases,

        isNorth = isNorth,
        timesByMonthSouth = timesByMonthSouth,
        timesByMonthNorth = timesByMonthNorth,

        location = location,

        shadowSize = shadowSize
    )
}

fun BugEntity.toDomain(): Bug {
    return Bug(
        userId = userId,
        number = number,
        name = name,
        imageUrl = imageUrl,
        renderUrl = renderUrl,
        isCollected = isCollected,
        rarity = rarity,
        totalCatch = totalCatch,
        sellNook = sellNook,
        tankWidth = tankWidth,
        tankLength = tankLength,
        catchphrases = catchphrases,

        isNorth = isNorth,
        timesByMonthNorth = timesByMonthNorth,
        timesByMonthSouth = timesByMonthSouth,

        location = location,
    )
}

fun Bug.toData(): BugEntity {
    return BugEntity(
        userId = userId,
        number = number,
        name = name,
        imageUrl = imageUrl,
        renderUrl = renderUrl,
        isCollected = isCollected,
        rarity = rarity,
        totalCatch = totalCatch,
        sellNook = sellNook,
        tankWidth = tankWidth,
        tankLength = tankLength,
        catchphrases = catchphrases,

        isNorth = isNorth,
        timesByMonthNorth = timesByMonthNorth,
        timesByMonthSouth = timesByMonthSouth,

        location = location,
    )
}

fun SeaCreatureEntity.toDomain(): SeaCreature {
    return SeaCreature(
        userId = userId,
        number = number,
        name = name,
        imageUrl = imageUrl,
        renderUrl = renderUrl,
        isCollected = isCollected,
        rarity = rarity,
        totalCatch = totalCatch,
        sellNook = sellNook,
        tankWidth = tankWidth,
        tankLength = tankLength,
        catchphrases = catchphrases,

        isNorth = isNorth,
        timesByMonthNorth = timesByMonthNorth,
        timesByMonthSouth = timesByMonthSouth,
    )
}

fun SeaCreature.toData(): SeaCreatureEntity {
    return SeaCreatureEntity(
        userId = userId,
        number = number,
        name = name,
        imageUrl = imageUrl,
        renderUrl = renderUrl,
        isCollected = isCollected,
        rarity = rarity,
        totalCatch = totalCatch,
        sellNook = sellNook,
        tankWidth = tankWidth,
        tankLength = tankLength,
        catchphrases = catchphrases,

        isNorth = isNorth,
        timesByMonthNorth = timesByMonthNorth,
        timesByMonthSouth = timesByMonthSouth,
    )
}