package com.jonatbergn.core.interact

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class InteractionConfig private constructor() {

    internal var delay: InteractionRetryContext.() -> Duration = { 1.seconds }
    internal var shouldRetry: InteractionRetryContext.() -> Boolean = { true }

    fun delay(delay: InteractionRetryContext.() -> Duration) {
        this.delay = delay
    }

    fun retryIf(shouldRetryOnException: InteractionRetryContext.() -> Boolean) {
        this.shouldRetry = shouldRetryOnException
    }

    companion object Factory {
        operator fun invoke(builder: InteractionConfig.() -> Unit = {}) = InteractionConfig().apply(builder)
    }
}
