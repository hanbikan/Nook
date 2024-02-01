package com.hanbikan.nook.core.domain.model

data class TutorialTask(
    val id: Int = 0,
    val userId: Int,
    override val name: String,
    override val isDone: Boolean,
    val day: Int,
) : Completable