package com.jonatbergn.core.iceandfire.foundation.local

import app.cash.turbine.test
import com.jonatbergn.core.iceandfire.foundation.ClockMock
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity.Companion.fooEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockPages.FIRST_PAGE
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockPages.SECOND_PAGE
import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class LocalTest : BaseTest() {

    private val clock = ClockMock()
    private val local = LocalImpl<MockEntity>(clock)

    @Test
    fun nonExistingEntity() {
        assertNull(LocalImpl<MockEntity>().get(""))
    }

    @Test
    fun putGetSingle() {
        val foo = fooEntity()
        local.put(foo)
        assertSame(foo, local.get(foo.url))
    }

    @Test
    fun putPages() = runTest {
        local.pageFlow.test {
            assertEquals(PageCollection(clock.now(), null, emptySet()), awaitItem())
            clock += Duration.seconds(1)
            local.put(FIRST_PAGE)
            assertEquals(PageCollection(clock.now(), FIRST_PAGE, emptySet()), awaitItem())
            clock += Duration.seconds(1)
            local.put(SECOND_PAGE)
            assertEquals(PageCollection(clock.now(), FIRST_PAGE, setOf(SECOND_PAGE)), awaitItem())
        }
    }

    @Test
    fun notifyChanged() = runTest {
        local.pageFlow.test {
            assertEquals(PageCollection(clock.now(), null, emptySet()), awaitItem())
            clock += Duration.seconds(1)
            local.put(FIRST_PAGE)
            local.notifyChanged()
            assertEquals(PageCollection(clock.now(), FIRST_PAGE, emptySet()), awaitItem())
            clock += Duration.seconds(1)
            local.notifyChanged()
            assertEquals(PageCollection(clock.now(), FIRST_PAGE, emptySet()), awaitItem())
        }
    }
}
