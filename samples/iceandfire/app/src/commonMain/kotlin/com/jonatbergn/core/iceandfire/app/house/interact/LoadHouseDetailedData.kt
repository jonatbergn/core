package com.jonatbergn.core.iceandfire.app.house.interact

import com.jonatbergn.core.iceandfire.app.State
import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import com.jonatbergn.core.iceandfire.foundation.entity.Repo.Companion.plus
import com.jonatbergn.core.iceandfire.foundation.interaction.Interaction
import com.jonatbergn.core.interact.invoke
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class LoadHouseDetailedData(
    private val state: MutableStateFlow<State>,
    private val houseRepo: Repo<House>,
    private val characterRepo: Repo<Character>,
    private val pointer: Pointer<House>,
) : Interaction {

    override suspend fun invoke(): Unit = coroutineScope {
        state { copy(loadingHouse = true) }
        state { copy(houses = houseRepo + pointer) }
        val house = houseRepo.entities[pointer]!!
        awaitAll(
            async { state { copy(characters = characterRepo + house.currentLord) } },
            async { state { copy(characters = characterRepo + house.heir) } },
            async { state { copy(characters = characterRepo + house.founder) } },
            async { state { copy(characters = characterRepo + house.swornMembers) } },
            async { state { copy(houses = houseRepo + house.overlord) } },
        )
        state { copy(loadingHouse = false) }
    }
}
