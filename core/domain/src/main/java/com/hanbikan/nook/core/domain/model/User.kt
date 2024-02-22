package com.hanbikan.nook.core.domain.model

data class User(
    val id: Int = 0,
    val name: String,
    val islandName: String,
    val tutorialDay: Int = 0,
) {
    companion object {
        val DEFAULT = User(id = 0, name = "", islandName = "", tutorialDay = 0)
        const val NAME_MAX_LENGTH = 10
        const val ISLAND_NAME_MAX_LENGTH = 10
    }
}