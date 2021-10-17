package com.jonatbergn.core.test

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlinx.coroutines.withTimeout

actual abstract class BaseTest {
    @OptIn(
        ExperimentalTime::class,
        DelicateCoroutinesApi::class
    )
    actual fun <T> runTest(timeout: Duration, block: suspend CoroutineScope.() -> T) {
        GlobalScope.promise { withTimeout(timeout) { block() } }
    }
}
