package com.hanbikan.nook.core.database.translator

import com.hanbikan.nook.core.database.entity.TutorialTaskEntity
import com.hanbikan.nook.core.domain.model.TutorialTask

fun TutorialTaskEntity.toDomain(): TutorialTask {
    return TutorialTask(id, userId, name, isDone, detailDescription, detailImageId, day)
}

fun TutorialTask.toData(): TutorialTaskEntity {
    return TutorialTaskEntity(id, userId, name, isDone, detailDescription, detailImageId, day)
}