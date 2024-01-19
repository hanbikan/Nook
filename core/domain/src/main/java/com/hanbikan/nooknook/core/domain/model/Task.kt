package com.hanbikan.nooknook.core.domain.model

data class Task(
    val id: Int,
    val userId: Int,
    val name: String,
    val isDone: Boolean
)