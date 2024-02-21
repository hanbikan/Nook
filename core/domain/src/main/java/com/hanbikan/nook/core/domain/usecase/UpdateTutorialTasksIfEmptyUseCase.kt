package com.hanbikan.nook.core.domain.usecase

/**
 * 모든 계정을 돌며 TutorialTask가 비었을 경우 이를 초기화하는 유스케이스입니다. 초기화 로직이 안드로이드의 리소스에 의존하기 때문에
 * 상위 안드로이드 모듈에서 DI로 구현체를 넣어주는 방식을 선택하였습니다.
 */
interface UpdateTutorialTasksIfEmptyUseCase {
    suspend operator fun invoke()
}