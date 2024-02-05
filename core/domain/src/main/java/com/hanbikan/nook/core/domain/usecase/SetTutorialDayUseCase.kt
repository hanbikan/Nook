package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.AppStateRepository
import javax.inject.Inject

class SetTutorialDayUseCase @Inject constructor(
    private val appStateRepository: AppStateRepository
) {
    suspend operator fun invoke(tutorialDay: Int) {
        return appStateRepository.setTutorialDay(tutorialDay)
    }
}