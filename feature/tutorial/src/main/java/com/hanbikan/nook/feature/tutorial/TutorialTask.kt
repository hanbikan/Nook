package com.hanbikan.nook.feature.tutorial

import com.hanbikan.nook.core.domain.model.TutorialTask

fun TutorialTask.Companion.createInitialTutorialTasks(userId: Int): List<TutorialTask> = listOf(
    TutorialTask(day = 0, name = "NPC들의 지시를 받아 튜토리얼 진행하기", detailDescription = "내 집과 주민의 집 위치는 나중에 변경 가능해요.", userId = userId, isDone = false),
    TutorialTask(day = 0, name = "너굴에게 침대 받고 텐트 안에 설치하고 자기", userId = userId, isDone = false),

    TutorialTask(day = 1, name = "[필수] 생물 5마리 기증하고 부엉 텐트 위치 결정하기", detailDescription = "나중에는 모든 종류의 생물을 박물관에 기증하게 되기 때문에 어떤 생물이든 기증하셔도 돼요.", userId = userId, isDone = false),
    TutorialTask(day = 1, name = "5,000마일 상환하기", userId = userId, isDone = false),
    TutorialTask(day = 1, name = "다음 날까지 기다리기", detailDescription = "동물의 숲은 실제 시간, 날짜, 계절을 반영하는 슬로우 라이프 게임이에요. 이제부터 침대에 누워서 자도 다음 날로 넘어가지 않는 대신, 실제 시간으로 오전 5시가 되면 다음 날로 넘어가게 돼요.", userId = userId, isDone = false),

    TutorialTask(day = 2, name = "[필수] 부엉 텐트에서 삽, 장대 레시피 얻기", detailDescription = "과일을 1개 먹으면 바위나 나무 1개를 완전히 제거할 수 있어요. 하지만 바위에서만 나오는 중요한 아이템이 있어서 깨지 않는 걸 추천드려요. 바위는 하루에 1개씩 랜덤한 자리에 생기고 최대 6개까지만 생겨요.", userId = userId, isDone = false),
    TutorialTask(day = 2, name = "[필수] 생물 15개 기증하기", userId = userId, isDone = false),
    TutorialTask(day = 2, name = "[필수] 상점 재료 모으기: 목재 30개, 부드러운 목재 30개, 단단한 목재 30개, 철광석 30개", detailDescription = "철광석은 바위에서만 나오는 귀중한 아이템이어서 하루만에 30개를 얻기가 힘들어요.\n<Tips.1> 사진처럼 땅을 파고 구멍과 돌 사이에 서서 돌을 빠르게 치면 아이템을 최대 8개 얻을 수 있어요.\n<Tips.2> 너굴 포트에서 2,000 마일로 구매할 수 있는 마일 여행권으로 마일 섬에 가서 돌을 캘 수도 있어요. (섬 아래 비행장 -> 모리에게 말 걸기 -> 외출할래 -> 마일 여행권을 쓸래)\n*다른 유저들과 통신할 때 마일 티켓 1개는 10만~20만 벨에 거래되니 아껴서 사용합시다!", detailImageId = R.drawable.eight_rocks, userId = userId, isDone = false),
    TutorialTask(day = 2, name = "98,000벨 상환하기", userId = userId, isDone = false),

    TutorialTask(day = 3, name = "[필수] 주민 집터 잡아주고 사다리 레시피 얻기", userId = userId, isDone = false),
    TutorialTask(day = 3, name = "[필수] 다리 건설하기", userId = userId, isDone = false),
    TutorialTask(day = 3, name = "[필수] 빈 집터에 가구 넣어주기", detailDescription = "빈 집터에 가구를 다 채우는 순간 그 집터에 들어올 주민이 랜덤으로 확정이 돼요.\n하지만 '마일런'으로 원하는 주민을 데려올 수도 있어요. 가구를 다 채우기 전에, 마일섬에 여러 번 방문하여 마음에 드는 주민이 나오면 섬으로 오라고 제안한 뒤 가구를 다 채워넣으면 돼요. (주의: 빈 집터가 없는데 마일섬에 방문할 경우 마일섬에 주민이 안 나와요.)\n마일런 외에도, 닌텐도 시간을 임의로 변경하여 진행하는 '캠핑장 노가다'와 amiibo를 구매하는 방법도 있으니 랜덤 주민으로 진행하셔도 무방해요. 3가지 방법 모두, 주민이 최대 주민수 만큼 있어도 기존 주민을 밀어낼 수 있어요.", userId = userId, isDone = false),
    TutorialTask(day = 3, name = "198,000벨 상환하기", userId = userId, isDone = false),
)