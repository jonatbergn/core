package com.jonatbergn.core.iceandfire.foundation.remote

import io.ktor.http.HeadersBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LinkTest {

    @Test
    fun testNext() {
        assertEquals(
            "/:-)",
            HeadersBuilder(1)
                .apply {
                    append(
                        "link",
                        """</:-)>; rel="next", <foobar>; rel="first", <foobar>; rel="last""""
                    )
                }.build()
                .link()
                .next
        )
    }

    @Test
    fun testNextMissing() {
        assertNull(
            HeadersBuilder(1)
                .apply {
                    append(
                        "link",
                        """<foobar>; rel="prev", <foobar>; rel="first", <foobar>; rel="last"""""
                    )
                }.build()
                .link()
                .next
        )
    }
}
