package com.hanbikan.nook.core.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class AvailabilityObject(
    val months: String,
    val time: String
)