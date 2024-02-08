package com.hanbikan.nook.feature.tutorial.model

import android.content.Context
import com.hanbikan.nook.core.domain.model.Detail
import com.hanbikan.nook.core.domain.model.TutorialTask
import com.hanbikan.nook.feature.tutorial.R

fun TutorialTask.Companion.createInitialTutorialTasks(
    userId: Int,
    context: Context,
): List<TutorialTask> = listOf(
    // DAY 0
    TutorialTask(
        day = 0,
        name = context.getString(R.string.tutorial_task_name1),
        details = listOf(Detail(description = context.getString(R.string.tutorial_task_detail_description1))),
        userId = userId,
        isDone = false
    ),
    TutorialTask(
        day = 0,
        name = context.getString(R.string.tutorial_task_name2),
        userId = userId,
        isDone = false
    ),

    // DAY 1
    TutorialTask(
        day = 1,
        name = context.getString(R.string.tutorial_task_name3),
        details = listOf(Detail(description = context.getString(R.string.tutorial_task_detail_description3))),
        userId = userId,
        isDone = false
    ),
    TutorialTask(
        day = 1,
        name = context.getString(R.string.tutorial_task_name4),
        details = listOf(
            Detail(description = context.getString(R.string.tutorial_task_detail_description4_1)),
            Detail(description = context.getString(R.string.tutorial_task_detail_description4_2)),
        ),
        userId = userId,
        isDone = false
    ),

    // DAY 2
    TutorialTask(
        day = 2,
        name = context.getString(R.string.tutorial_task_name5),
        details = listOf(
            Detail(description = context.getString(R.string.tutorial_task_detail_description5_1)),
            Detail(description = context.getString(R.string.tutorial_task_detail_description5_2)),
        ),
        userId = userId,
        isDone = false
    ),
    TutorialTask(
        day = 2,
        name = context.getString(R.string.tutorial_task_name6),
        userId = userId,
        isDone = false
    ),
    TutorialTask(
        day = 2,
        name = context.getString(R.string.tutorial_task_name7),
        details = listOf(
            Detail(description = context.getString(R.string.tutorial_task_detail_description7_1)),
            Detail(description = context.getString(R.string.tutorial_task_detail_description7_2), imageId = R.drawable.eight_rocks),
            Detail(description = context.getString(R.string.tutorial_task_detail_description7_3)),
            Detail(description = context.getString(R.string.tutorial_task_detail_description7_4)),
        ),
        userId = userId,
        isDone = false
    ),

    // DAY 3
    TutorialTask(
        day = 3,
        name = context.getString(R.string.tutorial_task_name8),
        userId = userId,
        isDone = false
    ),
    TutorialTask(
        day = 3,
        name = context.getString(R.string.tutorial_task_name9),
        userId = userId,
        isDone = false
    ),
    TutorialTask(
        day = 3,
        name = context.getString(R.string.tutorial_task_name10),
        details = listOf(
            Detail(description = context.getString(R.string.tutorial_task_detail_description10_1)),
            Detail(description = context.getString(R.string.tutorial_task_detail_description10_2)),
            Detail(description = context.getString(R.string.tutorial_task_detail_description10_3)),
            Detail(description = context.getString(R.string.tutorial_task_detail_description10_4)),
        ),
        userId = userId,
        isDone = false
    ),
)