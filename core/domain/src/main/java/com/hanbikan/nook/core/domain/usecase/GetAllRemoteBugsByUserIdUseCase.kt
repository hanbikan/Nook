package com.hanbikan.nook.core.domain.usecase

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import com.hanbikan.nook.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import java.util.Locale
import javax.inject.Inject

private val bugNameToKorean: Map<String, String> = mapOf(
    "common butterfly" to "배추흰나비",
    "Yellow butterfly" to "노랑나비",
    "tiger butterfly" to "호랑나비",
    "peacock butterfly" to "제비나비",
    "common bluebottle" to "청띠제비나비",
    "paper kite butterfly" to "왕얼룩나비",
    "great purple emperor" to "왕오색나비",
    "monarch butterfly" to "왕나비",
    "emperor butterfly" to "몰포나비",
    "agrias butterfly" to "아그리아스나비",
    "Rajah Brooke's birdwing" to "붉은목도리비단나비",
    "Queen Alexandra's birdwing" to "알렉산드라비단제비나비",
    "moth" to "나방",
    "Atlas moth" to "아틀라스나방",
    "Madagascan sunset moth" to "마다가스카르비단제비나방",
    "long locust" to "방아깨비",
    "migratory locust" to "섬풀무치",
    "rice grasshopper" to "메뚜기",
    "grasshopper" to "여치",
    "cricket" to "귀뚜라미",
    "bell cricket" to "방울벌레",
    "mantis" to "사마귀",
    "orchid mantis" to "연꽃사마귀",
    "honeybee" to "꿀벌",
    "wasp" to "벌",
    "brown cicada" to "유지매미",
    "robust cicada" to "참매미",
    "giant cicada" to "곰매미",
    "Walker cicada" to "애매미",
    "evening cicada" to "쓰르라미",
    "cicada shell" to "매미 허물",
    "red dragonfly" to "고추잠자리",
    "darner dragonfly" to "왕잠자리",
    "banded dragonfly" to "장수잠자리",
    "damselfly" to "실잠자리",
    "firefly" to "반디",
    "mole cricket" to "땅강아지",
    "pondskater" to "소금쟁이",
    "diving beetle" to "물방개",
    "giant water bug" to "물장군",
    "stinkbug" to "노린재",
    "man-faced stink bug" to "인면노린재",
    "ladybug" to "무당벌레",
    "tiger beetle" to "길앞잡이",
    "Jewel Beetle" to "비단벌레",
    "Violin Beetle" to "바이올린벌레",
    "citrus long-horned beetle" to "알락하늘소",
    "Rosalia Batesi Beetle" to "루리하늘소",
    "blue weevil beetle" to "유포루스바구미",
    "dung beetle" to "쇠똥구리",
    "earth-boring dung beetle" to "보라금풍뎅이",
    "Scarab Beetle" to "보석풍뎅이",
    "drone beetle" to "풍이",
    "Goliath Beetle" to "골리앗왕꽃무지",
    "Saw Stag" to "톱사슴벌레",
    "Miyama Stag" to "사슴벌레",
    "giant stag" to "왕사슴벌레",
    "Rainbow Stag" to "뮤엘러리사슴벌레",
    "cyclommatus stag" to "엘라푸스가위사슴벌레",
    "Golden Stag" to "황금사슴벌레",
    "Giraffe Stag" to "기라파톱사슴벌레",
    "horned dynastid" to "장수풍뎅이",
    "horned atlas" to "코카서스장수풍뎅이",
    "horned elephant" to "코끼리장수풍뎅이",
    "horned hercules" to "헤라클레스장수풍뎅이",
    "walking stick" to "긴수염대벌레",
    "walking leaf" to "잎사귀벌레",
    "bagworm" to "도롱이벌레",
    "ant" to "개미",
    "hermit crab" to "소라게",
    "wharf roach" to "갯강구",
    "fly" to "파리",
    "mosquito" to "모기",
    "flea" to "벼룩",
    "snail" to "달팽이",
    "Pill Bug" to "공벌레",
    "centipede" to "지네",
    "spider" to "거미",
    "tarantula" to "타란툴라",
    "scorpion" to "전갈"
)

private val bugLocationToKorean: Map<String, String> = mapOf(
    "Flying" to "공중",
    "Flying near blue, purple, and black flowers" to "검점, 파랑, 보라색 꽃 주변",
    "Flying near flowers" to "꽃 주변",
    "Flying near light sources" to "불빛 근처",
    "Flying near water" to "물 주변",
    "Flying near trash or rotten turnips" to "쓰레기, 썩은 무, 분수 불꽃의 재",
    "On the ground" to "풀밭",
    "On trees (any kind)" to "나무",
    "On rivers and ponds" to "물 위",
    "On flowers" to "꽃 위",
    "On white flowers" to "흰 꽃 위",
    "On trees (hardwood and cedar)" to "나무(활엽수, 침엽수)",
    "On tree stumps" to "그루터기",
    "On palm trees" to "야자수",
    "On beach rocks" to "해변 암반",
    "On villagers" to "주민의 몸",
    "On rocks and bushes" to "바위, 낮은 묘목",
    "On spoiled turnips/candy/lollipops" to "썩은 무, 사탕",
    "Underground" to "땅 속(울음 소리)",
    "Pushing snowballs" to "눈덩이",
    "From hitting rocks" to "바위 치기",
    "Disguised under trees" to "나무 밑(가구로 위장)",
    "Disguised on shoreline" to "해변(소라껍데기로 위장)",
    "Shaking trees" to "나무 흔들기",
    "Shaking trees (hardwood and cedar)" to "나무 흔들기(활엽수, 침엽수)",
    "Shaking non-fruit hardwood trees or cedar trees" to "나무 흔들기(열매 없는 활엽수, 침엽수)",
)

class GetAllRemoteBugsByUserIdUseCase @Inject constructor(
    private val remoteCollectionRepository: RemoteCollectionRepository,
    private val userRepository: UserRepository,
    private val appStateRepository: AppStateRepository,
) {
    suspend operator fun invoke(userId: Int): List<Bug> {
        val user = userRepository.getUserById(userId).first()
        val language = appStateRepository.getLanguage().first()

        return remoteCollectionRepository.getAllBugs(
            userId = userId,
            isNorth = user?.isNorth ?: true
        ).map {
            if (language == Locale.KOREAN.language) {
                it.copy(
                    name = bugNameToKorean.getOrElse(it.name) { it.name },
                    location = bugLocationToKorean.getOrElse(it.location) { it.location },
                )
            } else {
                it
            }
        }
    }
}