package com.hanbikan.nook.core.domain.model.common

data class TimeRange(
    val startMonth: Int,
    val endMonth: Int,
    val hourRange: String, // "4 AM - 6 AM"
)