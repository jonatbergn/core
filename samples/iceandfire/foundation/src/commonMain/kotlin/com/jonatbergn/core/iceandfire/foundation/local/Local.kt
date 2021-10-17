package com.jonatbergn.core.iceandfire.foundation.local

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import kotlinx.coroutines.flow.StateFlow

/**
 * Interface to access local resources of type [T].
 */
interface Local<T : Entity> {

    /**
     * A flow which publishes a [PageCollection] whenever underlining data was changed or [notifyChanged]
     * was called.
     */
    val pageFlow: StateFlow<PageCollection<T>>

    /**
     * @param url the identifier of a resource
     * @return the resource or null if no resource exists for the provided url
     */
    fun get(url: String): T?

    /**
     * @param entity the resource which will be stored
     */
    fun put(entity: T)

    /**
     * @param page the page of resources which will be stored
     */
    fun put(page: Page<T>)

    /**
     * Calling this method will publish a new [PageCollection] to the [pageFlow]
     */
    fun notifyChanged()
}
