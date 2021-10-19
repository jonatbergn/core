package com.jonatbergn.core.exception

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen

private fun <T> Flow<T>.handleExceptions(
    exceptionHandling: ExceptionHandling<Flow<T>>,
) = retryWhen { cause, attempt -> exceptionHandling.retryPredicate(cause, attempt) }
    .catchFallback(exceptionHandling.fallback)

fun <T> Flow<T>.handleExceptions(
    builder: ExceptionHandlingBuilder<Flow<T>>.() -> Unit,
) = handleExceptions(ExceptionHandlingBuilder<Flow<T>>().apply(builder).build())
