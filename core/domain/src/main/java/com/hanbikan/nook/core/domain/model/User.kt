package com.hanbikan.nook.core.domain.model

data class User(
    val id: Int = 0,
    val name: String,
    val islandName: String,
    val tutorialDay: Int = 0,
)