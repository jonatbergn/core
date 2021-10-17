package com.jonatbergn.core.exception

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen

fun <T> Flow<T>.handleExceptions(
    exceptionHandling: ExceptionHandling<Flow<T>>,
) = retryWhen { cause, attempt -> exceptionHandling.retryPredicate(cause, attempt) }
    .catchFallback(exceptionHandling.fallback)
