package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.ClockMock
import com.jonatbergn.core.iceandfire.foundation.local.LocalImpl
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockFetchDependents
import com.jonatbergn.core.iceandfire.foundation.mock.remote.MockRemote
import kotlinx.coroutines.Dispatchers
import kotlin.time.ExperimentalTime

@ExperimentalTime
class RepoTest {

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
        dispatcher = Dispatchers.Default,//todo
        remote = remote,
        local = local,
        fetchDetails = MockFetchDependents(local) {
            listOf(fetchAsync(it.dependent, local, remote))
        },
    ) { first.url }

//    @Test
//    fun scenario() = runBlocking {
//        repo.observePages().test {
//
//            assertEquals(PageCollection(clock.now(), null, emptySet()), awaitItem())
//
//            clock += 1.seconds
//            repo.fetchNextPage()
//            assertEquals(PageCollection(clock.now(), first, emptySet()), awaitItem())
//
//            clock += 1.seconds
//            repo.fetchNextPage()
//            assertEquals(PageCollection(clock.now(), first, setOf(second)), awaitItem())
//
//            clock += 1.seconds
//            repo.fetchNextPage()
//            assertEquals(PageCollection(clock.now(), first, setOf(second, third)), awaitItem())
//
//            // we reached the end pages. calling fetch should not have any effects
//            clock += 1.seconds
//            repo.fetchNextPage()
//            assertFailsWith<TimeoutCancellationException> { withTimeout(1.seconds) { awaitItem() } }
//
//            // test fetch one fetches details (in this case ant -> frog from remote)
//            clock += 1.seconds
//            assertTrue(ant.dependent.fetched == null)
//            assertNull(ant.dependent.value)
//            repo.fetchOne(ant.url)
//            awaitItem()
//            assertTrue(ant.dependent.fetched == true)
//            assertSame(ant.dependent.value, frog)
//
//            // test fetch one fetches details (in this case mouse -> raven from memory)
//            clock += 1.seconds
//            assertTrue(mouse.dependent.fetched == null)
//            assertNull(mouse.dependent.value)
//            repo.fetchOne(mouse.url)
//            // do not expect next event, as we retrieved the depending entity from cache
//            assertFailsWith<TimeoutCancellationException> { withTimeout(1.seconds) { awaitItem() } }
//            assertTrue(mouse.dependent.fetched == true)
//            assertSame(mouse.dependent.value, raven)
//        }
//    }
}
