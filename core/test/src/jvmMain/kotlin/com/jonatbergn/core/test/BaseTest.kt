package com.jonatbergn.core.test

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

actual abstract class BaseTest {
    @OptIn(ExperimentalTime::class)
    actual fun <T> runTest(timeout: Duration, block: suspend CoroutineScope.() -> T) {
        runBlocking { withTimeout(timeout) { block() } }
    }
}
