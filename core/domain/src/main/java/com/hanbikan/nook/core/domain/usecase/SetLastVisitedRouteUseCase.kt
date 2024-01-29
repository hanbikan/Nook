package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.repository.AppStateRepository
import javax.inject.Inject

class SetLastVisitedRouteUseCase @Inject constructor(
    private val appStateRepository: AppStateRepository
) {
    suspend operator fun invoke(route: String) {
        appStateRepository.setLastVisitedRoute(route)
    }
}