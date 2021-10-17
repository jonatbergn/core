package com.jonatbergn.core.exception

import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList

@ExperimentalTime
class AwaitFlowTest : BaseTest() {

    @Test
    fun timeoutThrowsTimeoutException() = runTest {
        assertFailsWith<TimeoutCancellationException> {
            flow {
                emit("a")
                delay(100)
                emit("b")
                delay(900)
                emit("c")
            }
                .awaitWithin(milliseconds(500)) { it == "c" }
                .toList()
        }
    }

    @Test
    fun timeoutReceivesValues() = runTest {
        assertEquals(
            listOf("a", "b"),
            flow {
                emit("a")
                delay(100)
                emit("b")
                delay(900)
                emit("c")
            }
                .awaitWithin(milliseconds(500)) { it == "c" }
                .catch { if (it !is TimeoutCancellationException) throw it }
                .toList()
        )
    }

    @Test
    fun noTimeout() = runTest {
        assertEquals(
            listOf("a", "b", "c"),
            flow {
                emit("a")
                delay(100)
                emit("b")
                delay(400)
                emit("c")
            }
                .awaitWithin(milliseconds(900)) { it == "c" }
                .toList()
        )
    }
}
