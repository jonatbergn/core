package com.jonatbergn.core.iceandfire.app

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.house.interact.LoadHouseDetailedData
import com.jonatbergn.core.iceandfire.app.house.interact.LoadNextHousePage
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AppStore {

    val state: StateFlow<State>

    suspend fun loadNextHousePage()
    suspend fun loadHouseDetails(house: Pointer<House>)

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

    override suspend fun loadNextHousePage() = LoadNextHousePage(
        state,
        houseRepo,
        characterRepo,
    ).invoke()

    override suspend fun loadHouseDetails(house: Pointer<House>) = LoadHouseDetailedData(
        state,
        houseRepo,
        characterRepo,
        house,
    ).invoke()
}
