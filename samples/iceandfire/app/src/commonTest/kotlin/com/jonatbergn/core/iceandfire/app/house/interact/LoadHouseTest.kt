package com.jonatbergn.core.iceandfire.app.house.interact

import app.cash.turbine.test
import com.jonatbergn.core.iceandfire.app.State
import com.jonatbergn.core.iceandfire.app.house.FakeHouses.house01
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.mock.repo.MockRepo
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LoadHouseTest {

    private val state = MutableStateFlow(State())
    private val loadHouse = LoadHouseDetailedData(
        state = state,
        houseRepo = MockRepo(
            onFetch = { house01 }
        ),
        characterRepo = MockRepo(),
        pointer = house01.pointer,
    )

    @Test
    fun test() = runTest {
        state.test {
            loadHouse()
            awaitItem() shouldBe State()
            awaitItem() shouldBe State(loadingHouse = true)
            awaitItem() shouldBe State(houses = persistentMapOf(house01.pointer to house01), loadingHouse = true)
            awaitItem() shouldBe State(houses = persistentMapOf(house01.pointer to house01), loadingHouse = false)
        }
    }
}
