package com.hanbikan.nook.feature.tutorial.model

import android.content.Context
import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.feature.tutorial.R

fun TutorialTask.Companion.createInitialTutorialTasks(
    userId: Int,
    context: Context
): List<TutorialTask> = listOf(
    TutorialTask(day = 0, name = context.getString(R.string.tutorial_task_name1), detailDescription = context.getString(R.string.tutorial_task_detail_description1), userId = userId, isDone = false),
    TutorialTask(day = 0, name = context.getString(R.string.tutorial_task_name2), userId = userId, isDone = false),

    TutorialTask(day = 1, name = context.getString(R.string.tutorial_task_name3), detailDescription = context.getString(R.string.tutorial_task_detail_description3), userId = userId, isDone = false),
    TutorialTask(day = 1, name = context.getString(R.string.tutorial_task_name4), detailDescription = context.getString(R.string.tutorial_task_detail_description4), userId = userId, isDone = false),

    TutorialTask(day = 2, name = context.getString(R.string.tutorial_task_name5), detailDescription = context.getString(R.string.tutorial_task_detail_description5), userId = userId, isDone = false),
    TutorialTask(day = 2, name = context.getString(R.string.tutorial_task_name6), userId = userId, isDone = false),
    TutorialTask(day = 2, name = context.getString(R.string.tutorial_task_name7), detailDescription = context.getString(R.string.tutorial_task_detail_description7), detailImageId = R.drawable.eight_rocks, userId = userId, isDone = false),

    TutorialTask(day = 3, name = context.getString(R.string.tutorial_task_name8), userId = userId, isDone = false),
    TutorialTask(day = 3, name = context.getString(R.string.tutorial_task_name9), userId = userId, isDone = false),
    TutorialTask(day = 3, name = context.getString(R.string.tutorial_task_name10), detailDescription = context.getString(R.string.tutorial_task_detail_description10), userId = userId, isDone = false),
)