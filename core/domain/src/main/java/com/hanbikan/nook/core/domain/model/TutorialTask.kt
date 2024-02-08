package com.hanbikan.nook.core.domain.model

data class TutorialTask(
    val id: Int = 0,
    val userId: Int,
    val day: Int,
    override val name: String,
    override val isDone: Boolean,
    override val detail: Detail? = null,
) : Completable, HasDetail {
    companion object // For companion extension functions outside
}