package com.hanbikan.nook.feature.museum

import com.hanbikan.nook.core.common.getCurrentHour
import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.common.MonthToTimes.Companion.ALL_DAY
import com.hanbikan.nook.core.domain.model.common.Monthly
import com.hanbikan.nook.core.domain.model.common.parseTimeRange
import kotlin.math.ceil

enum class CollectibleScreenViewType {
    LOADING, OVERALL, MONTHLY_GENERAL, MONTHLY_HOUR,
}

sealed class CollectibleScreenUiState(val chipIndex: Int?) {

    object Loading : CollectibleScreenUiState(chipIndex = null)

    class OverallView(val collectibleList: List<Collectible>) : CollectibleScreenUiState(chipIndex = 0)

    sealed class MonthlyView(
        val month: Int,
        val isNorth: Boolean,
    ) : CollectibleScreenUiState(chipIndex = 1) {

        class GeneralView(collectibleList: List<Collectible>, month: Int, isNorth: Boolean) : MonthlyView(month, isNorth) {
            val collectibleListForMonth: List<Collectible> =
                getCollectibleListForMonth(collectibleList, month)

            private fun getCollectibleListForMonth(
                collectibleList: List<Collectible>,
                month: Int
            ): List<Collectible> {
                return collectibleList.filter {
                    it is Monthly && it.belongsToMonth(month, isNorth)
                }
            }
        }

        class HourView(
            collectibleList: List<Collectible>,
            month: Int,
            isNorth: Boolean,
            private val minuteOffset: Int
        ) : MonthlyView(month, isNorth) {
            val startHourToCollectibleListForMonth: Map<Int, List<Collectible>> =
                getStartHourToCollectibleListForMonth(collectibleList, month)

            /**
             * startHour에 대응하는 endHour를 반환합니다.
             */
            fun getEndHourByStartHour(startHour: Int): Int {
                val hours = startHourToCollectibleListForMonth.keys.sorted()
                val nextHourIndex = hours.indexOfFirst { it == startHour } + 1
                return hours.getOrElse(nextHourIndex) { 24 }
            }

            fun isStartHourCurrentHourRange(startHour: Int): Boolean {
                val endHour = getEndHourByStartHour(startHour)
                val currentHour = getCurrentHour(minuteOffset)
                return currentHour in startHour until endHour
            }

            /**
             * 현재 시간에 해당하는 startHour key를 반환합니다.
             */
            fun getCurrentHourKey(): Int {
                val hours = startHourToCollectibleListForMonth.keys.sorted()
                hours.forEach { startHour ->
                    if (isStartHourCurrentHourRange(startHour)) {
                        return startHour
                    }
                }
                return ALL_DAY_KEY
            }

            fun getScrollIndexForKey(
                hourKey: Int,
                itemsPerRow: Int
            ): Int {
                var scrollIndex = 1
                startHourToCollectibleListForMonth.forEach { (startHour, collectibleList) ->
                    if (startHour < hourKey) {
                        scrollIndex += 2 + ceil(
                            (collectibleList.count().toFloat() / itemsPerRow)
                        ).toInt()
                    }
                }
                return scrollIndex
            }

            /**
             * Returns {0: <Collectible List 1>, 4: <Collectible List 2>, ..., 21: <Collectible List 3>}
             * which is merged by time range for same lists.
             */
            private fun getStartHourToCollectibleListForMonth(
                collectibleList: List<Collectible>,
                month: Int
            ): Map<Int, List<Collectible>> {
                val startHourToCollectibleListForMonth: MutableMap<Int, List<Collectible>> =
                    mutableMapOf()

                val hourToCollectibleListForMonth =
                    getHourToCollectibleListForMonth(collectibleList, month)
                hourToCollectibleListForMonth.forEach { (hour, collectibleListForHour) ->
                    // Skip if current collectible list is the same as the previous list.
                    if (hour - 1 >= 0 && collectibleListForHour == hourToCollectibleListForMonth[hour - 1]) {
                        return@forEach
                    }

                    startHourToCollectibleListForMonth[hour] = collectibleListForHour
                }

                return startHourToCollectibleListForMonth.toMap()
            }

            /**
             * Returns {0: <Collectible List>, 1: <Collectible List>, ..., 23: <Collectible List>}
             */
            private fun getHourToCollectibleListForMonth(
                collectibleList: List<Collectible>,
                month: Int
            ): Map<Int, List<Collectible>> {
                val hourToCollectibleListForMonth = buildMap<Int, MutableList<Collectible>> {
                    put(-1, mutableListOf()) // for always available
                    repeat(24) { hour ->
                        put(hour, mutableListOf())
                    }
                }

                collectibleList.forEach { item ->
                    if (item is Monthly && item.belongsToMonth(month, isNorth)) {
                        val times = item.getCurrentMonthToTimes(isNorth).getTimesOrNull(month)
                        // 항상 잡을 수 있는 생물은 ALL_DAY_KEY에 추가합니다.
                        if (times == ALL_DAY) {
                            hourToCollectibleListForMonth[ALL_DAY_KEY]?.add(item)
                        } else {
                            val hours = times?.parseTimeRange() ?: listOf()
                            hours.forEach { hour ->
                                hourToCollectibleListForMonth[hour]?.add(item)
                            }
                        }
                    }
                }

                return hourToCollectibleListForMonth
                    .mapValues { it.value.toList() } // MutableList to List
                    .toMap()
            }

            companion object {
                const val ALL_DAY_KEY = -1
            }
        }
    }
}