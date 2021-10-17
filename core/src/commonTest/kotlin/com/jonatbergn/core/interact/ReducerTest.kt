package com.jonatbergn.core.interact

import kotlin.test.Test
import kotlin.test.assertEquals

class ReducerTest {

    private val reducer = reducer<String, Int> { "$this, $it" }

    @Test
    fun reduce() {
        assertEquals(
            (0..10).joinToString { "$it" },
            (1..10).fold("0") { state, i -> reducer(state, i) }
        )
    }

    @Test
    fun filter() {
        assertEquals(
            "0",
            (1L..10L).fold("0") { state, i -> reducer(state, i) }
        )
    }
}
