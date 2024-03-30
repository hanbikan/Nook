package com.hanbikan.nook.core.domain.model

interface Collectable {
    val name: String
    val imageUrl: String
    val isCollected: Boolean
}

fun List<Collectable>.calculateProgress(): Float {
    return if (isEmpty()) {
        0f
    } else {
        count { it.isCollected }.toFloat() / count()
    }
}