package com.jonatbergn.cars.car.interact

import app.cash.turbine.test
import com.jonatbergn.cars.FakeCars.carStubsPage
import com.jonatbergn.cars.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.repo.FakeRepo
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LoadCarStubsTest {

    @Test
    fun loadCarStubsTest() = runTest {

        val state = MutableStateFlow(State())

        var fetches = 0

        val loadCarStubs = LoadCarStubs(
            state,
            FakeRepo(
                onHasMorePagesToFetch = { ++fetches <= 1 },
                onFetchNextPage = { carStubsPage }
            )
        )

        state.test {
            loadCarStubs()
            awaitItem() shouldBe State(
                loadingNextCarPage = false,
            )
            awaitItem() shouldBe State(
                loadingNextCarPage = true,
            )
            awaitItem() shouldBe State(
                loadingNextCarPage = true,
                carStubs = carStubsPage.associateBy { it.pointer },
            )
            awaitItem() shouldBe State(
                loadingNextCarPage = false,
                carStubs = carStubsPage.associateBy { it.pointer },
            )
        }
    }
}
