package com.jonatbergn.core.iceandfire.foundation.interact

import com.jonatbergn.core.iceandfire.foundation.interact.LoadNextPageTest.Event.Completed
import com.jonatbergn.core.iceandfire.foundation.interact.LoadNextPageTest.Event.InFlight
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity
import com.jonatbergn.core.iceandfire.foundation.mock.repo.MockRepo
import com.jonatbergn.core.interact.UseCase.Companion.interactWith
import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

@OptIn(
    ExperimentalTime::class,
    ExperimentalCoroutinesApi::class
)
class LoadNextPageTest : BaseTest() {

    object Action
    enum class Event { InFlight, Completed }

    @Test
    fun interaction() = runTest {
        val repo = MockRepo<MockEntity>()
        val events = object : LoadNextPage<Action>(
            retry = { _, _ -> false },
            repo = repo,
        ) {
            override fun Flow<*>.filter() = filterIsInstance<Action>()
            override suspend fun SendChannel<Any>.onInFlight() = send(InFlight)
            override suspend fun SendChannel<Any>.onComplete() = send(Completed)
        }.interactWith(flowOf(Action)).toList()
        assertEquals(listOf(InFlight, Completed), events)
        assertEquals(1, repo.fetchNextPageInvocations)
    }
}
