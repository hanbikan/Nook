package com.hanbikan.nook.core.domain.model

interface TimeSensitive {
    val timesByMonth: Map<Int, String> // 1: "NA", 3: "4 PM - 9 AM", 9: "All day"
}