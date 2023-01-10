package com.jonatbergn.core.iceandfire.foundation.local

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import kotlinx.collections.immutable.ImmutableMap

class FakeLocal<T : Entity>(
    override var pages: PageCollection<T>? = null,
) : Local<T> {
    override val all: ImmutableMap<Entity.Pointer<T>, T> = throw NotImplementedError()
    override fun get(url: String) = throw NotImplementedError()
    override fun put(entity: T) = throw NotImplementedError()
    override fun put(page: Page<T>) = throw NotImplementedError()
}
