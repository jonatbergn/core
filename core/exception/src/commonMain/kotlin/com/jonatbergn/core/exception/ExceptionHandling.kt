package com.jonatbergn.core.exception

data class ExceptionHandling<F>(
    val retryPredicate: suspend (cause: Throwable, attempt: Long) -> Boolean = { _, _ -> false },
    val fallback: Throwable.() -> F = { throw this },
)