package com.jonatbergn.core.exception

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

/**
 * Scans the flow for an event which matches the given [select] condition for a given [duration].
 * If no match was received within [duration], [TimeoutException] will be thrown.
 *
 * @param duration the timeout [Duration]
 * @param select a select functions to check if a given condition matches
 */
@OptIn(
    ExperimentalTime::class,
    ExperimentalCoroutinesApi::class,
)
fun <T> Flow<T>.awaitWithin(duration: Duration, select: (T) -> Boolean) = channelFlow {
    val events = Channel<T>()
    val collector = launch { collect { events.send(it) } }
    try {
        var received = false
        while (collector.isActive)
            if (!received) withTimeout(duration) {
                val event = events.receive()
                received = select(event)
                send(event)
            } else send(events.receive())
    } finally {
        collector.cancel()
        events.close()
    }
}
