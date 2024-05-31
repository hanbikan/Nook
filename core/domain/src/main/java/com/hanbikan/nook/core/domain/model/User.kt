package com.hanbikan.nook.core.domain.model

data class User(
    val id: Int = 0,
    val name: String,
    val islandName: String,
    val tutorialDay: Int = 0,
    val isNorth: Boolean,
) {
    companion object {
        val DEFAULT = User(id = 0, name = "", islandName = "", tutorialDay = 0, isNorth = true)
        const val NAME_MAX_LENGTH = 10
        const val ISLAND_NAME_MAX_LENGTH = 10
    }
}