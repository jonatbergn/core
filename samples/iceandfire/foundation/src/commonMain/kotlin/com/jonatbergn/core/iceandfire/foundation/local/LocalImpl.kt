package com.jonatbergn.core.iceandfire.foundation.local

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Companion.pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.Clock

/**
 * @param clock the [Clock] used for creation timestamps
 */
class LocalImpl<T : Entity> : Local<T> {

    //todo make this threadsafe
    override var all = persistentMapOf<Pointer<T>, T>()

    private val morePages = mutableSetOf<Page<T>>()
    private var startPage: Page<T>? = null

    override val pages: PageCollection<T>?
        get() {
            return PageCollection(
                start = startPage ?: return null,
                more = morePages,
            )
        }

    override fun get(url: String) = all[Pointer(url)]

    override fun put(entity: T) {
        all = all.put(entity.pointer, entity)
    }

    override fun put(page: Page<T>) {
        all = all.putAll(page.associateBy { it.pointer })
        if (startPage == null) startPage = page else morePages.add(page)
    }
}
