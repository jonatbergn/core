package com.jonatbergn.core.iceandfire.foundation.local

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import kotlinx.collections.immutable.ImmutableMap

/**
 * Interface to access local resources of type [T].
 */
interface Local<T : Entity> {

    val pages: PageCollection<T>?

    val all: ImmutableMap<Entity.Pointer<T>, T>

    /**
     * @param url the identifier of a resource
     * @return the resource or null if no resource exists for the provided url
     */
    operator fun get(url: String): T?

    /**
     * @param entity the resource which will be stored
     */
    fun put(entity: T)

    /**
     * @param page the page of resources which will be stored
     */
    fun put(page: Page<T>)

}
