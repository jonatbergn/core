package com.jonatbergn.core.interact

import kotlinx.coroutines.*

abstract class SupervisedInteraction<Result>(
    private val config: InteractionConfig = InteractionConfig(),
) : Interaction<Result> {

    final override suspend fun invoke(): Result {
        var retryCount = 0
        beforeInvoke()
        while (true) {
            try {
                val result = supervisorScope { onInvoke() }
                afterInvoke()
                return result
            } catch (e: Throwable) {
                val context = InteractionRetryContext(++retryCount, e)
                if (e is CancellationException || !config.shouldRetry(context)) {
                    afterInvoke()
                    throw e
                }
                delay(config.delay(context))
            }
        }
    }

    abstract fun beforeInvoke()

    abstract suspend fun onInvoke(): Result

    abstract fun afterInvoke()
}
