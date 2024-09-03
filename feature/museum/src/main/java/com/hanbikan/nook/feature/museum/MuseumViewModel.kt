package com.hanbikan.nook.feature.museum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.common.getCurrentMonth
import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.common.Monthly
import com.hanbikan.nook.core.domain.model.common.calculateProgress
import com.hanbikan.nook.core.domain.model.common.filterForMonth
import com.hanbikan.nook.core.domain.model.common.updateOnLocal
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MuseumViewModel @Inject constructor(
    getActiveUserUseCase: GetActiveUserUseCase,
    private val collectionRepository: CollectionRepository,
    private val appStateRepository: AppStateRepository,
) : ViewModel() {

    private val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val isNorth: StateFlow<Boolean> = activeUser.mapLatest {
        it?.isNorth ?: true
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    val hasMuseumGuideShown: StateFlow<Boolean> = appStateRepository.getHasMuseumGuideShown()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    // collectible list
    @OptIn(ExperimentalCoroutinesApi::class)
    val bugs: StateFlow<List<Bug>> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                collectionRepository.getAllBugsByUserId(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    val fishes: StateFlow<List<Fish>> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                collectionRepository.getAllFishesByUserId(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    val seaCreatures: StateFlow<List<SeaCreature>> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                collectionRepository.getAllSeaCreaturesByUserId(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    val bugProgress: StateFlow<Float?> = bugs
        .mapLatest {
            withContext(Dispatchers.IO) {
                it.calculateProgress()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val fishProgress: StateFlow<Float?> = fishes
        .mapLatest {
            withContext(Dispatchers.IO) {
                it.calculateProgress()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val seaCreatureProgress: StateFlow<Float?> = seaCreatures
        .mapLatest {
            withContext(Dispatchers.IO) {
                it.calculateProgress()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val overallProgress: StateFlow<Float?> = combine(
        bugProgress,
        fishProgress,
        seaCreatureProgress
    ) { bugProgress, fishProgress, seaCreatureProgress ->
        if (bugProgress != null && fishProgress != null && seaCreatureProgress != null) {
            (bugProgress + fishProgress + seaCreatureProgress) / 3.0f
        } else {
            0f
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    // 현재 수집 가능
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentlyCollectibleBugs: StateFlow<List<Collectible>?> = bugs
        .mapLatest {
            withContext(Dispatchers.IO) {
                filterCurrentlyCollectible(it).sortedBy { it.isCollected }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentlyCollectibleFishes: StateFlow<List<Collectible>?> = fishes
        .mapLatest {
            withContext(Dispatchers.IO) {
                filterCurrentlyCollectible(it).sortedBy { it.isCollected }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentlyCollectibleSeaCreature: StateFlow<List<Collectible>?> = seaCreatures
        .mapLatest {
            withContext(Dispatchers.IO) {
                filterCurrentlyCollectible(it).sortedBy { it.isCollected }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    // 이번 달 컬렉션
    private val collectiblesForMonth: StateFlow<List<Collectible>> =
        combine(fishes, bugs, seaCreatures) { fishes, bugs, seaCreatures ->
            val activeUser = activeUser.value
            if (fishes.isNotEmpty() && bugs.isNotEmpty() && seaCreatures.isNotEmpty() && activeUser != null) {
                withContext(Dispatchers.IO) {
                    val fishesForMonth =
                        fishes.filterForMonth(
                            getCurrentMonth(activeUser.minuteOffset),
                            activeUser.isNorth
                        )
                    val bugsForMonth = bugs.filterForMonth(
                        getCurrentMonth(activeUser.minuteOffset),
                        activeUser.isNorth
                    )
                    val seaCreaturesForMonth =
                        seaCreatures.filterForMonth(
                            getCurrentMonth(activeUser.minuteOffset),
                            activeUser.isNorth
                        )
                    fishesForMonth + bugsForMonth + seaCreaturesForMonth
                }
            } else {
                listOf()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    @OptIn(ExperimentalCoroutinesApi::class)
    val uncollectedForMonth: StateFlow<List<Collectible>?> =
        collectiblesForMonth.mapLatest { collectiblesForMonth ->
            withContext(Dispatchers.IO) {
                collectiblesForMonth.filter { !it.isCollected }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uncollectedCountForMonth: StateFlow<Int?> = collectiblesForMonth.mapLatest {
        withContext(Dispatchers.IO) {
            it.count { !it.isCollected }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    // loading
    val isLoading: StateFlow<Boolean> = combine(
        overallProgress,
        currentlyCollectibleBugs,
        currentlyCollectibleFishes,
        currentlyCollectibleSeaCreature,
        collectiblesForMonth,
        uncollectedCountForMonth
    ) { items ->
        if (items.all { it != null }) {
            delay(150)
            false
        } else {
            true
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    // Dialogs
    private val _collectibleForDetailCollectibleDialog: MutableStateFlow<Collectible?> =
        MutableStateFlow(null)
    val collectibleForDetailCollectibleDialog = _collectibleForDetailCollectibleDialog.asStateFlow()

    private val _collectibleForCollectDialog: MutableStateFlow<Collectible?> =
        MutableStateFlow(null)
    val collectibleForCollectDialog = _collectibleForCollectDialog.asStateFlow()

    fun onClickCollectibleItem(collectible: Collectible) {
        _collectibleForCollectDialog.value = collectible
    }

    fun onLongClickCollectibleItem(collectible: Collectible) {
        _collectibleForDetailCollectibleDialog.value = collectible
    }

    fun onDismissDetailCollectibleDialog() {
        _collectibleForDetailCollectibleDialog.value = null
    }

    fun onConfirmCollectDialog() {
        viewModelScope.launch(Dispatchers.IO) {
            collectibleForCollectDialog.value?.updateOnLocal(collectionRepository)
            _collectibleForCollectDialog.value = null
        }
    }

    fun onDismissCollectDialog() {
        _collectibleForCollectDialog.value = null
    }

    fun getIsNorthForActiveUser(): Boolean {
        return activeUser.value?.isNorth ?: true
    }

    fun setHasMuseumGuideShownTrue() {
        viewModelScope.launch(Dispatchers.IO) {
            appStateRepository.setHasMuseumGuideShown(true)
        }
    }

    private fun filterCurrentlyCollectible(collectibles: List<Collectible>): List<Collectible> {
        val activeUser = activeUser.value
        return if (activeUser != null) {
            collectibles.filter {
                if (it is Monthly) it.isCurrentlyCollectible(
                    activeUser.isNorth,
                    activeUser.minuteOffset
                ) else {
                    false
                }
            }
        } else {
            listOf()
        }
    }
}