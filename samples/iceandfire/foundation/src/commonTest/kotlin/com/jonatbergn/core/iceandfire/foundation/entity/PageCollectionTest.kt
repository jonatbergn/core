package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.entity.FakeEntities.page01
import com.jonatbergn.core.iceandfire.foundation.entity.FakeEntities.page02
import com.jonatbergn.core.iceandfire.foundation.entity.FakeEntities.page03
import com.jonatbergn.core.iceandfire.foundation.entity.FakeEntities.pages
import kotlin.test.Test
import kotlin.test.assertEquals

class PageCollectionTest {

    @Test
    fun itemListWithStartAndWithoutMore() {
        assertEquals(
            page01.toList(),
            PageCollection(
                page01,
                emptySet(),
            ).flatten()
        )
    }

    @Test
    fun itemListWithStartAndMore() {
        assertEquals(
            pages.flatten(),
            PageCollection(
                page01,
                setOf(page03, page02),
            ).toList().flatten()
        )
    }
}
