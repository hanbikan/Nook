package com.hanbikan.nook.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class HemisphereAvailability(
    val availability_array: List<AvailabilityObject>,
    val times_by_month: TimesByMonth,
    val months: String,
    val months_array: List<Int>
)