package com.jonatbergn.core.test

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.CoroutineScope

expect abstract class BaseTest() {
    @OptIn(ExperimentalTime::class)
    fun <T> runTest(timeout: Duration = Duration.seconds(5), block: suspend CoroutineScope.() -> T)
}
