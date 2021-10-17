package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockPages.FIRST_PAGE
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockPages.SECOND_PAGE
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.datetime.Clock.System.now

class PageCollectionTest {

    @Test
    fun itemListWithoutStartAndWithoutMore() {
        assertTrue(
            PageCollection<Entity>(
                now(),
                null,
                emptySet()
            ).itemList.isEmpty()
        )
    }

    @Test
    fun itemListWithStartAndWithoutMore() {
        assertEquals(
            listOf(FIRST_PAGE).map { it.data }.flatten(),
            PageCollection(
                now(),
                FIRST_PAGE,
                emptySet()
            ).itemList
        )
    }

    @Test
    fun itemListWithStartAndMore() {
        assertEquals(
            listOf(FIRST_PAGE, SECOND_PAGE).map { it.data }.flatten(),
            PageCollection(
                now(),
                FIRST_PAGE,
                setOf(SECOND_PAGE)
            ).itemList
        )
    }

    @Test
    fun emptyPageCollectionIsNotEnd() {
        assertFalse(
            PageCollection<Entity>(
                now(),
                null,
                emptySet()
            ).isEnd
        )
    }

    @Test
    fun pageCollectionWithStartAndWithoutNextAndWithoutMoreIsEnd() {
        assertTrue(
            PageCollection<Entity>(
                now(),
                Page("0", emptyList(), null),
                emptySet()
            ).isEnd
        )
    }

    @Test
    fun pageCollectionWithStartAndNextAndWithoutMoreIsNotEnd() {
        assertFalse(
            PageCollection<Entity>(
                now(),
                Page("0", emptyList(), "1"),
                emptySet()
            ).isEnd
        )
    }

    @Test
    fun pageCollectionWithStartAndWithMoreAndWithNextIsNotEnd() {
        assertFalse(
            PageCollection<Entity>(
                now(),
                Page("0", emptyList(), "1"),
                setOf(Page("1", emptyList(), "2"))
            ).isEnd
        )
    }
}
