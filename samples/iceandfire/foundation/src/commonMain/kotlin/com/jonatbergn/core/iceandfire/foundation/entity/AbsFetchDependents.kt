package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.local.Local
import com.jonatbergn.core.iceandfire.foundation.remote.Remote
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * @param locals all locals which might be interested in updates of entities for type [T]
 */
abstract class AbsFetchDependents<T : Entity>(
    private vararg val locals: Local<*>,
) : FetchDependents<T> {

    /**
     * Fetches a dependent of type [T]. If the dependent entity was fetched from [Remote], it will
     * be added to [local] and all other [locals] will be notified about that change.
     *
     * @param dependent the [Dependent] to fetch
     * @param local the [Local] holding instances of [T]
     * @param remote the [Remote] used to fetch [T]s, which do not exist in [local].
     *
     * @return A deferred which fetches the [Dependent.value] when awaited.
     */
    suspend fun <T : Entity> fetchAsync(
        dependent: Dependent<T>,
        local: Local<T>,
        remote: Remote<T>,
    ) = coroutineScope {
        if (dependent.fetched == true || dependent.url.isNullOrBlank()) {
            async { dependent.fetched = true }
        } else {
            async {
                val fromMemory = local.get(dependent.url)
                if (fromMemory != null) {
                    dependent.value = fromMemory
                    dependent.fetched = true
                } else {
                    try {
                        val fromRemote = remote.getOne(dependent.url)
                        local.put(fromRemote)
                        dependent.value = fromRemote
                        dependent.fetched = true
                    } catch (_: Throwable) {
                        dependent.fetched = false
                    }
                    locals.filter { it != local }.forEach(Local<*>::notifyChanged)
                }
            }
        }
    }
}
