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

internal class LoadCharacter(
    private val state: MutableStateFlow<State>,
    private val pointer: Pointer<Character>,
    private val houseRepo: Repo<House>,
    private val characterRepo: Repo<Character>,
    loadDependents: suspend (Character) -> Unit,
) : LoadEntity<Character>(
    pointer = pointer,
    repo = characterRepo,
    loadDependents = loadDependents
) {

    override fun beforeInvoke() = state { copy(loadingCharacters = loadingCharacters + pointer) }

    override fun onEntityFetched() = state { copy(characters = characterRepo.entities) }

    override fun afterInvoke() = state {
        copy(
            loadingCharacters = loadingCharacters - pointer,
            houses = houseRepo.entities,
            characters = characterRepo.entities
        )
    }
}
