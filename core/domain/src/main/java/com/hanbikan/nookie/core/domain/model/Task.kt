package com.hanbikan.nookie.core.domain.model

data class Task(
    val id: Int,
    val userId: Int,
    val name: String,
    val isDone: Boolean
)