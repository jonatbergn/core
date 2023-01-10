package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockPages.FIRST_PAGE
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockPages.SECOND_PAGE
import kotlin.test.Test
import kotlin.test.assertEquals

class PageCollectionTest {

    @Test
    fun itemListWithStartAndWithoutMore() {
        assertEquals(
            listOf(FIRST_PAGE).flatten(),
            PageCollection(
                FIRST_PAGE,
                emptySet()
            ).toList().flatten()
        )
    }

    @Test
    fun itemListWithStartAndMore() {
        assertEquals(
            listOf(FIRST_PAGE, SECOND_PAGE).flatten(),
            PageCollection(
                FIRST_PAGE,
                setOf(SECOND_PAGE)
            ).toList().flatten()
        )
    }
}
