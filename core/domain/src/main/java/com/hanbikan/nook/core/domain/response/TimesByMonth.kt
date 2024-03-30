package com.hanbikan.nook.core.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class TimesByMonth(
    val `1`: String,
    val `2`: String,
    val `3`: String,
    val `4`: String,
    val `5`: String,
    val `6`: String,
    val `7`: String,
    val `8`: String,
    val `9`: String,
    val `10`: String,
    val `11`: String,
    val `12`: String
)

fun TimesByMonth.toMap(): Map<Int, String> {
    return mapOf(
        1 to `1`,
        2 to `2`,
        3 to `3`,
        4 to `4`,
        5 to `5`,
        6 to `6`,
        7 to `7`,
        8 to `8`,
        9 to `9`,
        10 to `10`,
        11 to `11`,
        12 to `12`,
    )
}