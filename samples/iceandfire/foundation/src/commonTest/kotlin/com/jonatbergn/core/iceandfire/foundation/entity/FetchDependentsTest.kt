package com.jonatbergn.core.iceandfire.foundation.entity

import app.cash.turbine.test
import com.jonatbergn.core.iceandfire.foundation.local.LocalImpl
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity.Companion.barEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity.Companion.fooEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockFetchDependents
import com.jonatbergn.core.iceandfire.foundation.mock.remote.MockRemote
import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.awaitAll

@ExperimentalTime
class FetchDependentsTest : BaseTest() {

    @Test
    fun notifyWhenFetchedFromRemote() = runTest {
        val foo = barEntity()
        val bar = barEntity()
        val otherLocalA = LocalImpl<MockEntity>()
        val otherLocalB = LocalImpl<MockEntity>()
        val local = LocalImpl<MockEntity>().apply { put(bar) }
        val remote = MockRemote<MockEntity>().apply {
            onGetOne = { if (it == foo.url) foo else throw RuntimeException() }
        }
        val fetch = MockFetchDependents<MockEntity>(
            local,
            otherLocalA,
            otherLocalB
        ) {
            listOf(fetchAsync(it.dependent, local, remote))
        }
        fetch(bar).awaitAll()
        otherLocalA.pageFlow.test { awaitItem() }
        otherLocalB.pageFlow.test { awaitItem() }
        local.pageFlow.test { awaitItem() }
    }

    @Test
    fun givenFetchingDependentFailsSetFetchedToFalse() = runTest {
        val bar = barEntity()
        val local = LocalImpl<MockEntity>().apply { put(bar) }
        val remote = MockRemote<MockEntity>().apply {
            onGetOne = { throw RuntimeException() }
        }
        val fetch = MockFetchDependents<MockEntity>(local) {
            listOf(fetchAsync(it.dependent, local, remote))
        }
        fetch(bar).awaitAll()
        assertTrue(bar.dependent.fetched == false)
    }

    @Test
    fun givenFetchingDependentSucceedsSetFetchedToTrueAndCorrectValue() = runTest {
        val foo = fooEntity()
        val bar = barEntity()
        val local = LocalImpl<MockEntity>().apply { put(bar) }
        val remote = MockRemote<MockEntity>().apply {
            onGetOne = { if (it == foo.url) foo else throw IllegalStateException() }
        }
        val fetch = MockFetchDependents<MockEntity>(local) {
            listOf(fetchAsync(it.dependent, local, remote))
        }
        fetch(bar).awaitAll()
        assertTrue(bar.dependent.fetched == true)
        assertSame(foo, bar.dependent.value)
    }
}
