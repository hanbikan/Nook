package com.hanbikan.nook.feature.museum

import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.common.HasShadowSize
import com.hanbikan.nook.core.domain.model.common.LocationBased
import com.hanbikan.nook.feature.museum.model.CollectibleSequence

data class CollectibleSort(
    val nameResourceId: Int,
    val sort: (List<Collectible>) -> List<Collectible>
) {
    companion object {

        val SORT_BY_DEFAULT: CollectibleSort = CollectibleSort(R.string.sort_by_default) { it }

        val SORT_BY_IS_COLLECTED: CollectibleSort = CollectibleSort(R.string.sort_by_is_collected) { collectibles ->
            collectibles.sortedBy { it.isCollected }
        }

        val SORT_BY_LOCATION: CollectibleSort = CollectibleSort(R.string.sort_by_location) { collectibles ->
            // 첫 번째 원소가 LocationBased면 모두 LocationBased라고 가정
            if (collectibles.getOrNull(0) != null && collectibles[0] is LocationBased) {
                (collectibles as List<LocationBased>).sortedBy { it.location } as List<Collectible>
            } else {
                collectibles
            }
        }

        val SORT_BY_SHADOW_SIZE: CollectibleSort = CollectibleSort(R.string.sort_by_shadow_size) { collectibles ->
            if (collectibles.getOrNull(0) != null && collectibles[0] is HasShadowSize) {
                (collectibles as List<HasShadowSize>).sortedBy { it.shadowSize } as List<Collectible>
            } else {
                collectibles
            }
        }

        /**
         * [collectibleSequence]를 확인하여 가능한 [CollectibleSort] 리스트를 반환합니다.
         */
        fun getCollectibleSorts(collectibleSequence: CollectibleSequence): List<CollectibleSort> {
            val sorts: MutableList<CollectibleSort> = mutableListOf(SORT_BY_DEFAULT, SORT_BY_IS_COLLECTED)
            when (collectibleSequence) {
                CollectibleSequence.BUG -> {
                    sorts.add(SORT_BY_LOCATION)
                }
                CollectibleSequence.FISH -> {
                    sorts.add(SORT_BY_LOCATION)
                    sorts.add(SORT_BY_SHADOW_SIZE)
                }
                CollectibleSequence.SEA_CREATURE -> {
                    sorts.add(SORT_BY_LOCATION)
                    sorts.add(SORT_BY_SHADOW_SIZE)
                }
            }
            return sorts
        }
    }
}