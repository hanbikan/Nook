package com.hanbikan.nook.core.network.translator

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.response.BugResponse
import com.hanbikan.nook.core.domain.response.FishResponse
import com.hanbikan.nook.core.domain.response.SeaCreatureResponse
import com.hanbikan.nook.core.domain.response.toMap

fun FishResponse.toDomain(
    userId: Int,
    isNorth: Boolean,
): Fish {
    return Fish(
        userId = userId,
        name = name,
        number = number,
        imageUrl = image_url,
        timesByMonth = if (isNorth) north.times_by_month.toMap() else south.times_by_month.toMap(),
        isCollected = false,
        location = location,
    )
}

fun BugResponse.toDomain(
    userId: Int,
    isNorth: Boolean,
): Bug {
    return Bug(
        userId = userId,
        number = number,
        name = name,
        imageUrl = image_url,
        isCollected = false,
        timesByMonth = if (isNorth) north.times_by_month.toMap() else south.times_by_month.toMap(),
        location = location
    )
}

fun SeaCreatureResponse.toDomain(
    userId: Int,
    isNorth: Boolean,
): SeaCreature {
    return SeaCreature(
        userId = userId,
        number = number,
        name = name,
        imageUrl = image_url,
        isCollected = false,
        timesByMonth = if (isNorth) north.times_by_month.toMap() else south.times_by_month.toMap(),
    )
}