package com.hanbikan.nook.core.domain.model.common

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.repository.CollectionRepository

interface Collectible {
    val userId: Int
    val number: Int
    val name: String
    val imageUrl: String
    val renderUrl: String
    val isCollected: Boolean
    val rarity: String
    val totalCatch: Int
    val sellNook: Int
    val tankWidth: Float
    val tankLength: Float
    val catchphrases: List<String>
}

fun List<Collectible>.calculateProgress(): Float {
    return if (isEmpty()) {
        0f
    } else {
        count { it.isCollected }.toFloat() / count()
    }
}

fun <T> List<T>.filterForMonth(
    month: Int,
    isNorth: Boolean
): List<T> where T : Collectible, T : Monthly {
    return filter { it.belongsToMonth(month, isNorth) }
}

suspend fun Collectible.updateOnLocal(collectionRepository: CollectionRepository) {
    when (this) {
        is Fish -> {
            val fish = copy(isCollected = !isCollected)
            collectionRepository.updateFish(fish)
        }
        is Bug -> {
            val bug = copy(isCollected = !isCollected)
            collectionRepository.updateBug(bug)
        }
        is SeaCreature -> {
            val seaCreature = copy(isCollected = !isCollected)
            collectionRepository.updateSeaCreature(seaCreature)
        }
    }
}