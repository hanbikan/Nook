package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

private val nameToKorean: Map<String, String> = mapOf(
    "Bitterling" to "납줄개",
    "Pale chub" to "피라미",
    "Crucian carp" to "붕어",
    "Dace" to "황어",
    "Carp" to "잉어",
    "Koi" to "비단잉어",
    "Goldfish" to "금붕어",
    "Pop-eyed goldfish" to "툭눈금붕어",
    "Ranchu goldfish" to "난주",
    "Killifish" to "송사리",
    "Crawfish" to "가재",
    "Soft-shelled turtle" to "자라",
    "Snapping turtle" to "늑대거북",
    "Tadpole" to "올챙이",
    "Frog" to "개구리",
    "Freshwater goby" to "동사리",
    "Loach" to "미꾸라지",
    "Catfish" to "메기",
    "Giant snakehead" to "가물치",
    "Bluegill" to "블루길",
    "Yellow perch" to "옐로우퍼치",
    "Black bass" to "큰입배스",
    "Tilapia" to "틸라피아",
    "Pike" to "강꼬치고기",
    "Pond smelt" to "빙어",
    "Sweetfish" to "은어",
    "Cherry salmon" to "산천어",
    "Char" to "열목어",
    "Golden trout" to "금송어",
    "Stringfish" to "일본연어",
    "Salmon" to "연어",
    "King salmon" to "왕연어",
    "Mitten crab" to "참게",
    "Guppy" to "구피",
    "Nibble fish" to "닥터피시",
    "Angelfish" to "천사어",
    "Betta" to "베타",
    "Neon tetra" to "네온테트라",
    "Rainbowfish" to "레인보우피시",
    "Piranha" to "피라니아",
    "Arowana" to "아로와나",
    "Dorado" to "도라도",
    "Gar" to "가아",
    "Arapaima" to "피라루쿠",
    "Saddled bichir" to "엔드리케리",
    "Sturgeon" to "철갑상어",
    "Sea butterfly" to "클리오네",
    "Sea horse" to "해마",
    "Clown fish" to "흰동가리",
    "Surgeonfish" to "블루탱",
    "Butterfly fish" to "나비고기",
    "Napoleonfish" to "나폴레옹피시",
    "Zebra turkeyfish" to "쏠배감펭",
    "Blowfish" to "복어",
    "Puffer fish" to "가시복",
    "Anchovy" to "멸치",
    "Horse mackerel" to "전갱이",
    "Barred knifejaw" to "돌돔",
    "Sea bass" to "농어",
    "Red snapper" to "도미",
    "Dab" to "가자미",
    "Olive flounder" to "넙치",
    "Squid" to "오징어",
    "Moray eel" to "곰치",
    "Ribbon eel" to "리본장어",
    "Tuna" to "다랑어",
    "Blue marlin" to "청새치",
    "Giant trevally" to "무명갈전갱이",
    "Mahi-mahi" to "만새기",
    "Ocean sunfish" to "개복치",
    "Ray" to "가오리",
    "Saw shark" to "톱상어",
    "Hammerhead shark" to "귀상어",
    "Great white shark" to "상어",
    "Whale shark" to "고래상어",
    "Suckerfish" to "빨판상어",
    "Football fish" to "초롱아귀",
    "Oarfish" to "산갈치",
    "Barreleye" to "데메니기스",
    "Coelacanth" to "실러캔스"
)

private val locationToKorean: Map<String, String> = mapOf(
    "River" to "강",
    "Sea" to "바다",
    "Pier" to "부둣가",
    "Pond" to "연못",
    "River (clifftop)" to "절벽 위",
    "River (mouth)" to "하구",
    "Sea (raining)" to "바다(비 오는 날)"
)

class GetAllFishesByUserIdUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(userId: Int): Flow<List<Fish>> {
        return collectionRepository.getAllFishesByUserId(userId)
            .mapLatest { fishList ->
                fishList.map {
                    it.copy(
                        name = nameToKorean.getOrElse(it.name) { it.name },
                        location = locationToKorean.getOrElse(it.location) { it.location },
                    )
                }
            }
    }
}