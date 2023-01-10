package com.jonatbergn.core.iceandfire.android

import com.jonatbergn.core.iceandfire.android.house.HouseDetailsUiState
import com.jonatbergn.core.iceandfire.android.house.HouseGrossUiState
import com.jonatbergn.core.iceandfire.android.house.HouseListUiState
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.app.state.HouseDetailsState
import com.jonatbergn.core.iceandfire.app.state.HouseGrossState
import com.jonatbergn.core.iceandfire.app.state.HouseListState
import com.jonatbergn.core.iceandfire.app.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import kotlinx.collections.immutable.toImmutableList

interface UiState {

    val houseList: HouseListUiState

    fun houseDetails(house: Entity.Pointer<House>): HouseDetailsUiState

    companion object Factory {
        val State.asUiState get(): UiState = UiStateImpl(this)
    }
}

private data class UiStateImpl(
    private val state: State
) : UiState {

    private val HouseDetailsState.asUiState
        get() = HouseDetailsUiState(
            url = url,
            isLoading = isLoading,
            name = name.orEmpty(),
            lordName = lordName.orEmpty(),
            words = words.orEmpty(),
            titles = titles.orEmpty().toImmutableList(),
            coatOfArms = coatOfArms.orEmpty(),
        )

    private val HouseGrossState.asUiState
        get() = HouseGrossUiState(
            url = url,
            isLoading = isLoading,
            name = name.orEmpty(),
            words = words.orEmpty(),
            region = region.orEmpty(),
            lordName = lordName.orEmpty(),
            lordGender = lordGender.orEmpty(),
            diedOut = diedOut == true,
            founded = founded.orEmpty(),
        )

    private val HouseListState.asUiState
        get() = HouseListUiState(
            houses = houses.map { it.asUiState }.toImmutableList(),
            isMoreHousesAvailable = isMoreHousesAvailable,
            loadNextHousesInFlight = loadNextHousesInFlight,
        )

    override val houseList = state.houseList.asUiState

    override fun houseDetails(house: Entity.Pointer<House>) = state.houseDetails(house).asUiState

}
