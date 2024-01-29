package com.hanbikan.nook.core.domain.model

data class Task(
    val id: Int = 0,
    val userId: Int,
    val name: String,
    val isDone: Boolean
)