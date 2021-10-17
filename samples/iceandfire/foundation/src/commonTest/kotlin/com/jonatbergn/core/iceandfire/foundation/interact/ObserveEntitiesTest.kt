package com.jonatbergn.core.iceandfire.foundation.interact

import app.cash.turbine.test
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity
import com.jonatbergn.core.iceandfire.foundation.mock.repo.MockRepo
import com.jonatbergn.core.interact.UseCase.Companion.interactWith
import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.datetime.Clock.System.now

@OptIn(
    ExperimentalTime::class,
    ExperimentalCoroutinesApi::class
)
class ObserveEntitiesTest : BaseTest() {

    @Test
    fun interaction() = runTest {
        val pages = Channel<PageCollection<MockEntity>>()
        val repo = MockRepo(pages.receiveAsFlow())
        val page0 = PageCollection<MockEntity>(now(), Page("", emptyList(), ""), emptySet())
        val page1 = PageCollection<MockEntity>(now(), Page("", emptyList(), ""), emptySet())
        object : ObserveEntities<MockEntity>(repo) {
            override suspend fun SendChannel<Any>.onUpdate(
                entities: PageCollection<MockEntity>,
            ) = send(entities)
        }.interactWith(emptyFlow<Any>()).test {
            pages.send(page0)
            assertEquals(page0, awaitItem())
            pages.send(page1)
            assertEquals(page1, awaitItem())
        }
    }
}
