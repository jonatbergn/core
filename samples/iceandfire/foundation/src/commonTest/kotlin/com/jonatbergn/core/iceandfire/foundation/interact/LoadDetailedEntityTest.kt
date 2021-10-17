package com.jonatbergn.core.iceandfire.foundation.interact

import com.jonatbergn.core.iceandfire.foundation.interact.LoadDetailedEntityTest.Event.Completed
import com.jonatbergn.core.iceandfire.foundation.interact.LoadDetailedEntityTest.Event.InFlight
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
class LoadDetailedEntityTest : BaseTest() {

    data class Action(override val url: String) : LoadDetailedEntityAction
    enum class Event { InFlight, Completed }

    @Test
    fun interaction() = runTest {
        val url = "foobar"
        val repo = MockRepo<MockEntity>()
        val events = object : LoadDetailedEntity(repo) {
            override fun Flow<*>.filter() = filterIsInstance<Action>()
            override suspend fun SendChannel<Any>.onInFlight() = send(InFlight)
            override suspend fun SendChannel<Any>.onComplete() = send(Completed)
        }.interactWith(flowOf(Action(url))).toList()
        assertEquals(listOf(InFlight, Completed), events)
        assertEquals(listOf(url), repo.fetchOneInvocations)
    }
}
