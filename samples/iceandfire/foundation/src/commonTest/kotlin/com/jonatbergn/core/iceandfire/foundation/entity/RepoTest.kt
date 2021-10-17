package com.jonatbergn.core.iceandfire.foundation.entity

import app.cash.turbine.test
import com.jonatbergn.core.iceandfire.foundation.ClockMock
import com.jonatbergn.core.iceandfire.foundation.local.LocalImpl
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockFetchDependents
import com.jonatbergn.core.iceandfire.foundation.mock.remote.MockRemote
import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withTimeout

@ExperimentalTime
class RepoTest : BaseTest() {

    // no page data
    private val frog = MockEntity("frog", Dependent(null))

    // page data
    private val ape = MockEntity("ape", Dependent(null))
    private val snake = MockEntity("snake", Dependent(null))
    private val lion = MockEntity("lion", Dependent(null))
    private val raven = MockEntity("raven", Dependent(null))
    private val mouse = MockEntity("mouse", Dependent(raven.url))
    private val ant = MockEntity("ant", Dependent(frog.url))

    private val third = Page("3", listOf(mouse, ant), null)
    private val second = Page("2", listOf(lion, raven), third.url)
    private val first = Page("1", listOf(ape, snake), second.url)

    private val clock = ClockMock()
    private val remote = MockRemote(
        onGetOne = {
            when (it) {
                ape.url -> ape
                snake.url -> snake
                lion.url -> lion
                raven.url -> raven
                mouse.url -> mouse
                frog.url -> frog
                ant.url -> ant
                else -> throw NotImplementedError()
            }
        },
        onGetMany = {
            when (it) {
                first.url -> first
                second.url -> second
                third.url -> third
                else -> throw NotImplementedError()
            }
        }
    )
    private val local = LocalImpl<MockEntity>(clock)
    private val repo = RepoImpl(
        scope = CoroutineScope(Unconfined),
        remote = remote,
        local = local,
        fetchGross = MockFetchDependents(local) {
            emptyList()
        },
        fetchDetails = MockFetchDependents(local) {
            listOf(fetchAsync(it.dependent, local, remote))
        },
    ) { first.url }

    @Test
    fun scenario() = runTest(Duration.minutes(1)) {
        repo.observePages().test {

            assertEquals(PageCollection(clock.now(), null, emptySet()), awaitItem())

            clock += Duration.seconds(1)
            repo.fetchNextPage()
            assertEquals(PageCollection(clock.now(), first, emptySet()), awaitItem())

            clock += Duration.seconds(1)
            repo.fetchNextPage()
            assertEquals(PageCollection(clock.now(), first, setOf(second)), awaitItem())

            clock += Duration.seconds(1)
            repo.fetchNextPage()
            assertEquals(PageCollection(clock.now(), first, setOf(second, third)), awaitItem())

            // we reached the end pages. calling fetch should not have any effects
            clock += Duration.seconds(1)
            repo.fetchNextPage()
            assertFailsWith<TimeoutCancellationException> { withTimeout(Duration.seconds(1)) { awaitItem() } }

            // test fetch one fetches details (in this case ant -> frog from remote)
            clock += Duration.seconds(1)
            assertTrue(ant.dependent.fetched == null)
            assertNull(ant.dependent.value)
            repo.fetchOne(ant.url)
            awaitItem()
            assertTrue(ant.dependent.fetched == true)
            assertSame(ant.dependent.value, frog)

            // test fetch one fetches details (in this case mouse -> raven from memory)
            clock += Duration.seconds(1)
            assertTrue(mouse.dependent.fetched == null)
            assertNull(mouse.dependent.value)
            repo.fetchOne(mouse.url)
            // do not expect next event, as we retrieved the depending entity from cache
            assertFailsWith<TimeoutCancellationException> { withTimeout(Duration.seconds(1)) { awaitItem() } }
            assertTrue(mouse.dependent.fetched == true)
            assertSame(mouse.dependent.value, raven)
        }
    }
}
