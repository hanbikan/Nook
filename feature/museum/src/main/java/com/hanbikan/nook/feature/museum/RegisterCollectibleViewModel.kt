package com.hanbikan.nook.feature.museum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature
import com.hanbikan.nook.core.domain.model.User
import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.common.updateOnLocal
import com.hanbikan.nook.core.domain.repository.CollectionRepository
import com.hanbikan.nook.core.domain.usecase.GetActiveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterCollectibleViewModel @Inject constructor(
    getActiveUserUseCase: GetActiveUserUseCase,
    private val collectionRepository: CollectionRepository,
) : ViewModel() {

    private val activeUser: StateFlow<User?> = getActiveUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val isNorth: StateFlow<Boolean> = activeUser.mapLatest {
        it?.isNorth ?: true
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    @OptIn(ExperimentalCoroutinesApi::class)
    val bugs: StateFlow<List<Bug>?> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                collectionRepository.getAllBugsByUserId(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val fishes: StateFlow<List<Fish>?> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                collectionRepository.getAllFishesByUserId(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val seaCreatures: StateFlow<List<SeaCreature>?> = activeUser
        .flatMapLatest {
            if (it == null) {
                flowOf(listOf())
            } else {
                collectionRepository.getAllSeaCreaturesByUserId(it.id)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val isLoading: StateFlow<Boolean> = combine(bugs, fishes, seaCreatures) { items ->
        if (items.all { it != null }) {
            delay(150)
            false
        } else {
            true
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    fun onClickCollectibleItem(collectible: Collectible) {
        viewModelScope.launch(Dispatchers.IO) {
            collectible.updateOnLocal(collectionRepository)
        }
    }
}