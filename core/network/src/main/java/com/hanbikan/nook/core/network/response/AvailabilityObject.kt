package com.hanbikan.nook.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class AvailabilityObject(
    val months: String,
    val time: String
)