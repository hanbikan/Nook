package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.model.Bug
import com.hanbikan.nook.core.domain.model.Fish
import com.hanbikan.nook.core.domain.model.SeaCreature

interface CollectionRepository {

    fun getAllFishes(): List<Fish>

    fun getAllBugs(): List<Bug>

    fun getAllSeaCreatures(): List<SeaCreature>
}