package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import javax.inject.Inject

private val seaCreatureNameToKorean: Map<String, String> = mapOf(
    "seaweed" to "미역",
    "sea grapes" to "바다포도",
    "sea cucumber" to "해삼",
    "sea pig" to "바다돼지",
    "sea star" to "불가사리",
    "sea urchin" to "성게",
    "slate pencil urchin" to "연필성게",
    "sea anemone" to "말미잘",
    "moon jellyfish" to "보름달물해파리",
    "sea slug" to "갯민숭달팽이",
    "pearl oyster" to "진주조개",
    "mussel" to "지중해담치",
    "oyster" to "굴",
    "scallop" to "가리비",
    "whelk" to "수랑",
    "turban shell" to "소라",
    "abalone" to "전복",
    "gigas giant clam" to "대왕거거",
    "chambered nautilus" to "앵무조개",
    "octopus" to "문어",
    "umbrella octopus" to "우무문어",
    "vampire squid" to "흡혈오징어",
    "firefly squid" to "반딧불오징어",
    "gazami crab" to "꽃게",
    "Dungeness crab" to "던지니스크랩",
    "snow crab" to "대게",
    "red king crab" to "왕게",
    "acorn barnacle" to "따개비",
    "spider crab" to "키다리게",
    "tiger prawn" to "보리새우",
    "sweet shrimp" to "북쪽분홍새우",
    "mantis shrimp" to "갯가재",
    "spiny lobster" to "닭새우",
    "lobster" to "바닷가재",
    "giant isopod" to "자이언트 이소포드",
    "horseshoe crab" to "투구게",
    "sea pineapple" to "멍게",
    "spotted garden eel" to "가든일",
    "flatworm" to "납작벌레",
    "Venus' flower basket" to "해로동혈"
)

class GetAllRemoteSeaCreaturesByUserIdUseCase @Inject constructor(
    private val remoteCollectionRepository: RemoteCollectionRepository,
) {
    suspend operator fun invoke(userId: Int): List<SeaCreature> {
        return remoteCollectionRepository.getAllSeaCreatures(
            userId = userId,
            isNorth = true // TODO
        ).map {
            it.copy(
                name = seaCreatureNameToKorean.getOrElse(it.name) { it.name },
            )
        }
    }
}