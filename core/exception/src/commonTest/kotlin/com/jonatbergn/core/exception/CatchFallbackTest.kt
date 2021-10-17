package com.jonatbergn.core.exception

import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

@ExperimentalTime
class CatchFallbackTest : BaseTest() {

    data class Invocation(val cause: Throwable)

    @Test
    fun test() = runTest {

        val givenException = RuntimeException("Should be caught")
        val invocations = mutableListOf<Invocation>()

        val events = flow<Any> { throw givenException }
            .catchFallback { cause -> invocations.add(Invocation(cause)); flowOf("fallback") }
            .toList()

        assertEquals(
            listOf(Invocation(givenException)),
            invocations
        )
        assertEquals(listOf("fallback"), events)
    }
}
