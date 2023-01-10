package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.interact.LoadEntity
import com.jonatbergn.core.interact.Interaction
import com.jonatbergn.core.interact.SupervisedInteraction
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.flow.MutableStateFlow

internal class LoadHouse(
    private val state: MutableStateFlow<State>,
    private val pointer: Pointer<House>,
    private val houseRepo: Repo<House>,
    private val characterRepo: Repo<Character>,
    loadDependents: suspend (House) -> Unit,
) : LoadEntity<House>(
    pointer = pointer,
    repo = houseRepo,
    loadDependents = loadDependents,
) {

    override fun beforeInvoke() = state { copy(loadingHouses = loadingHouses + pointer) }

    override fun onEntityFetched() = state { copy(houses = houseRepo.entities) }

    override fun afterInvoke() = state {
        copy(
            loadingHouses = loadingHouses - pointer,
            houses = houseRepo.entities,
            characters = characterRepo.entities
        )
    }
}
