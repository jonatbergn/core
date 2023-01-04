package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.State
import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.Repo.Companion.allPagedEntityPointers
import com.jonatbergn.core.iceandfire.foundation.entity.interact.LoadNextEntityPage
import com.jonatbergn.core.interact.invoke
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.MutableStateFlow

class LoadNextHousePage(
    private val state: MutableStateFlow<State>,
    private val houseRepo: Repo<House>,
    private val characterRepo: Repo<Character>,
) : LoadNextEntityPage<House>(
    repo = houseRepo,
) {

    override suspend fun onFetchNextPageStarted() = state {
        copy(
            loadingNextHousePage = true,
            hasMoreHousePagesToFetch = null,
        )
    }

    override suspend fun onFetchNextPageSucceeded() = state {
        copy(
            houses = houseRepo.entities.toPersistentMap(),
            characters = characterRepo.entities.toPersistentMap(),

            loadingNextHousePage = false,
            hasMoreHousePagesToFetch = houseRepo.hasMorePagesToFetch,

            houseList = houseRepo.allPagedEntityPointers,
            characterList = characterRepo.allPagedEntityPointers,
        )
    }

    override suspend fun fetchDependents(pointer: Pointer<House>) = LoadHouseGrossData(
        state = state,
        pointer = pointer,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
    ).invoke()
}
