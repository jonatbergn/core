package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.interact.SupervisedInteraction
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoadGrossCharacterData(
    private val state: MutableStateFlow<State>,
    private val character: Character,
    private val houseRepo: Repo<House>,
    private val characterRepo: Repo<Character>,
) : SupervisedInteraction<Unit>() {

    override fun beforeInvoke() = state {
        copy(loadingCharacters = loadingCharacters + character.pointer)
    }

    override suspend fun onInvoke(): Unit = coroutineScope {
        buildList {
            add(character.father)
            add(character.mother)
        }.filterNotNull().forEach { launch { characterRepo.fetch(it) } }
        character.allegiances?.firstOrNull()?.let { launch { houseRepo.fetch(it) } }
    }


    override fun afterInvoke() = state {
        copy(loadingCharacters = loadingCharacters - character.pointer)
    }
}