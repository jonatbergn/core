package com.jonatbergn.core.iceandfire.foundation.local

import app.cash.turbine.test
import com.jonatbergn.core.iceandfire.foundation.ClockMock
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity.Companion.fooEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockPages.FIRST_PAGE
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockPages.SECOND_PAGE
import io.kotest.common.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.time.Duration.Companion.seconds

class LocalTest {

    private val clock = ClockMock()
    private val local = LocalImpl<MockEntity>(clock)

    @Test
    fun nonExistingEntity() {
        assertNull(LocalImpl<MockEntity>()[""])
    }

    @Test
    fun putGetSingle() {
        val foo = fooEntity()
        local.put(foo)
        assertSame(foo, local[foo.url])
    }

    @Test
    fun putPages() = runBlocking {
        local.pageFlow.test {
            assertEquals(PageCollection(clock.now(), null, emptySet()), awaitItem())
            clock += 1.seconds
            local.put(FIRST_PAGE)
            assertEquals(PageCollection(clock.now(), FIRST_PAGE, emptySet()), awaitItem())
            clock += 1.seconds
            local.put(SECOND_PAGE)
            assertEquals(PageCollection(clock.now(), FIRST_PAGE, setOf(SECOND_PAGE)), awaitItem())
        }
    }

    @Test
    fun notifyChanged() = runBlocking {
        local.pageFlow.test {
            assertEquals(PageCollection(clock.now(), null, emptySet()), awaitItem())
            clock += 1.seconds
            local.put(FIRST_PAGE)
            local.notifyChanged()
            assertEquals(PageCollection(clock.now(), FIRST_PAGE, emptySet()), awaitItem())
            clock += 1.seconds
            local.notifyChanged()
            assertEquals(PageCollection(clock.now(), FIRST_PAGE, emptySet()), awaitItem())
        }
    }
}
