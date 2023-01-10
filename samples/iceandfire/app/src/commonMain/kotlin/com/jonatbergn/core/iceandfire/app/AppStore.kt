package com.jonatbergn.core.iceandfire.app

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.house.interact.*
import com.jonatbergn.core.iceandfire.app.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AppStore {

    val state: StateFlow<State>

    suspend fun loadNextHousePage(): Page<House>?
    suspend fun loadHouseGross(house: Pointer<House>): House
    suspend fun loadHouseDetails(house: Pointer<House>): House
    suspend fun loadCharacterDetails(character: Pointer<Character>): Character
    suspend fun loadCharacterGross(character: Pointer<Character>): Character

    companion object Factory {

        operator fun invoke(
            context: AppContext,
        ): AppStore = with(context) {
            AppStoreImpl(
                state = state,
                houseRepo = houseRepo,
                characterRepo = characterRepo,
            )
        }
    }
}

private class AppStoreImpl(
    override val state: MutableStateFlow<State>,
    private val houseRepo: Repo<House>,
    private val characterRepo: Repo<Character>,
) : AppStore {

    private suspend fun loadHouseGross(house: House) = LoadGrossHouseData(
        state = state,
        house = house,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
    ).invoke()

    private suspend fun loadHouseDetails(house: House) = LoadDetailedHouseData(
        state = state,
        house = house,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
    ).invoke()

    private suspend fun loadCharacterDetails(character: Character) = LoadDetailedCharacterData(
        state = state,
        character = character,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
    ).invoke()

    private suspend fun loadCharacterGross(character: Character) = LoadGrossCharacterData(
        state = state,
        character = character,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
    ).invoke()

    override suspend fun loadNextHousePage() = LoadNextHousePage(
        state = state,
        houseRepo = houseRepo,
    ).invoke()

    override suspend fun loadHouseGross(house: Pointer<House>) = LoadHouse(
        state = state,
        pointer = house,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
        loadDependents = { loadHouseGross(it) },
    ).invoke()

    override suspend fun loadHouseDetails(house: Pointer<House>) = LoadHouse(
        state = state,
        pointer = house,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
        loadDependents = { loadHouseDetails(it) },
    ).invoke()

    override suspend fun loadCharacterDetails(character: Pointer<Character>) = LoadCharacter(
        state = state,
        pointer = character,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
        loadDependents = { loadCharacterDetails(it) },
    ).invoke()

    override suspend fun loadCharacterGross(character: Pointer<Character>) = LoadCharacter(
        state = state,
        pointer = character,
        houseRepo = houseRepo,
        characterRepo = characterRepo,
        loadDependents = { loadCharacterGross(it) },
    ).invoke()
}
