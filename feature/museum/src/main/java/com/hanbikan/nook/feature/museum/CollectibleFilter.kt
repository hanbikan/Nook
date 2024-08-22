package com.hanbikan.nook.feature.museum

import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.common.HasShadowSize
import com.hanbikan.nook.core.domain.model.common.LocationBased
import com.hanbikan.nook.feature.museum.model.CollectibleSequence

data class CollectibleFilter(
    val nameResourceId: Int,
    val filter: (List<Collectible>) -> List<Collectible>
) {
    companion object {

        val FILTER_BY_DEFAULT: CollectibleFilter = CollectibleFilter(R.string.filter_by_default) { it }

        val FILTER_BY_IS_COLLECTED: CollectibleFilter = CollectibleFilter(R.string.filter_by_is_collected) { collectibles ->
            collectibles.filter { it.isCollected }
        }

        val FILTER_BY_IS_NOT_COLLECTED: CollectibleFilter = CollectibleFilter(R.string.filter_by_is_not_collected) { collectibles ->
            collectibles.filter { !it.isCollected }
        }

        fun getCollectibleFilters(): List<CollectibleFilter> {
            return listOf(
                FILTER_BY_DEFAULT,
                FILTER_BY_IS_COLLECTED,
                FILTER_BY_IS_NOT_COLLECTED
            )
        }
    }
}