package com.jonatbergn.core.exception

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

fun <T> Flow<T>.catchFallback(fallback: suspend (Throwable) -> Flow<T>) = catch {
    fallback(it).collect { event -> emit(event) }
}
