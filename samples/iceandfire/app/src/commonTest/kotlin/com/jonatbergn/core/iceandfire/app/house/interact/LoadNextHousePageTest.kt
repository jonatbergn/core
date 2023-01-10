package com.jonatbergn.core.iceandfire.app.house.interact

import app.cash.turbine.test
import com.jonatbergn.core.iceandfire.app.house.FakeHouses.housePages
import com.jonatbergn.core.iceandfire.app.state.State
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.repo.FakeRepo
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LoadNextHousePageTest {

    private val state = MutableStateFlow(State())
    private val pageIterator = housePages.iterator()
    private val houseRepo = FakeRepo(
        onHasMorePagesToFetch = pageIterator::hasNext,
        onFetchNextPage = pageIterator::next,
        onFetch = { pointer -> housePages.flatten().first { it.pointer == pointer } }
    )
    private val loadNextHousePage = LoadNextHousePage(
        state = state,
        houseRepo = houseRepo,
        characterRepo = FakeRepo(),
        fetchDependents = { },
    )

    @Test
    fun loadThreeConsecutiveHousePages() = runTest {
        state.test {

            skipItems(1)

            loadNextHousePage()
            awaitItem() shouldBe State(
                hasMoreHousePagesToFetch = null,
                loadingNextHousePage = true
            )
            awaitItem() shouldBe State(
                houses = housePages.take(1).flatten().associateBy { it.pointer },
                pagedHouses = housePages.take(1),
                hasMoreHousePagesToFetch = true,
                loadingNextHousePage = false,
            )

            loadNextHousePage()
            awaitItem() shouldBe State(
                houses = housePages.take(2).flatten().associateBy { it.pointer },
                pagedHouses = housePages.take(1),
                hasMoreHousePagesToFetch = null,
                loadingNextHousePage = true,
            )
            awaitItem() shouldBe State(
                houses = housePages.take(2).flatten().associateBy { it.pointer },
                pagedHouses = housePages.take(2),
                hasMoreHousePagesToFetch = true,
                loadingNextHousePage = false,
            )

            loadNextHousePage()
            awaitItem() shouldBe State(
                houses = housePages.take(3).flatten().associateBy { it.pointer },
                pagedHouses = housePages.take(2),
                hasMoreHousePagesToFetch = null,
                loadingNextHousePage = true,
            )
            awaitItem() shouldBe State(
                houses = housePages.take(3).flatten().associateBy { it.pointer },
                pagedHouses = housePages.take(3),
                hasMoreHousePagesToFetch = false,
                loadingNextHousePage = false,
            )
        }
    }
}
