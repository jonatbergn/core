package com.jonatbergn.core.exception

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

private suspend fun <T> handleExceptions(
    function: (suspend () -> T),
    handling: ExceptionHandling<T>,
): T {
    var attempt = 0L
    while (true) {
        try {
            return function()
        } catch (cause: Throwable) {
            if (!handling.retryPredicate(cause, attempt++)) {
                return handling.fallback.invoke(cause)
            }
        }
    }
}

fun <T> CoroutineScope.async(
    start: CoroutineStart,
    value: suspend () -> T,
    handling: ExceptionHandlingBuilder<T>.() -> Unit,
): Deferred<T> = async(start = start) { handleExceptions(value, exceptionHandling(handling)) }
