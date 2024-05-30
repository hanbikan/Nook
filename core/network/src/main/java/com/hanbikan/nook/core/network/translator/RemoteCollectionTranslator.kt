package com.hanbikan.nook.core.network.translator

import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.response.FishResponse
import com.hanbikan.nook.core.domain.response.toMap

fun FishResponse.toDomain(
    userId: Int,
    isNorth: Boolean, // 북반구 여부
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