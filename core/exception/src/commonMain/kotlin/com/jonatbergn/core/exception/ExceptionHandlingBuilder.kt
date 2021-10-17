package com.jonatbergn.core.exception

class ExceptionHandlingBuilder<T>(
    private var retryPredicate: suspend (cause: Throwable, attempt: Long) -> Boolean = { _, _ -> false },
    private var fallback: (Throwable) -> T = { throw it },
) {

    fun retryWhen(block: suspend (cause: Throwable, attempt: Long) -> Boolean) {
        retryPredicate = block
    }

    fun fallBack(block: (Throwable) -> T) {
        fallback = block
    }

    internal fun build() = ExceptionHandling(
        retryPredicate = retryPredicate,
        fallback = fallback,
    )
}

fun <T> exceptionHandling(block: ExceptionHandlingBuilder<T>.() -> Unit): ExceptionHandling<T> =
    ExceptionHandlingBuilder<T>().apply(block).build()
