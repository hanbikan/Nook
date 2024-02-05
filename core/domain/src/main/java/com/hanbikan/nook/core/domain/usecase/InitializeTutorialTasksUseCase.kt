package com.hanbikan.nook.core.domain.usecase

/**
 * 특정 계정에 대한 [TutorialTask]를 초기화하는 유스케이스입니다. [TutorialTask]가 android resource id에 의존하기
 * 때문에 상위 안드로이드 모듈에서 DI로 구현체를 넣어주는 방식을 선택하였습니다.
 */
interface InitializeTutorialTasksUseCase {
    suspend operator fun invoke(userId: Int)
}