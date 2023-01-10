package com.jonatbergn.core.iceandfire.app.house.interact

import app.cash.turbine.test
import com.jonatbergn.core.iceandfire.app.house.FakeHouses.house01
import com.jonatbergn.core.iceandfire.app.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.repo.FakeRepo
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LoadHouseTest {

    private val state = MutableStateFlow(State())
    private val loadHouse = LoadHouse(
        state = state,
        pointer = house01.pointer,
        houseRepo = FakeRepo(
            onFetch = { house01 }
        ),
        characterRepo = FakeRepo(),
        loadDependents = { },
    )

    @Test
    fun test() = runTest {
        state.test {
            loadHouse()
            awaitItem() shouldBe State()
            awaitItem() shouldBe State(loadingHouses = listOf(house01.pointer))
            awaitItem() shouldBe State(
                houses = mapOf(house01.pointer to house01),
                loadingHouses = listOf(house01.pointer)
            )
            awaitItem() shouldBe State(houses = mapOf(house01.pointer to house01), loadingHouses = emptyList())
        }
    }
}
