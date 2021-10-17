package com.jonatbergn.core.iceandfire.foundation.local

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock

/**
 * @param clock the [Clock] used for creation timestamps
 */
class LocalImpl<T : Entity>(
    private val clock: Clock = Clock.System,
) : Local<T> {

    private var startPage: Page<T>? = null
    private val morePages = mutableSetOf<Page<T>>()
    private val entities = mutableMapOf<String, T>()
    override val pageFlow = MutableStateFlow(PageCollection<T>(clock.now(), null, emptySet()))

    private fun modify(block: () -> Unit) {
        block()
        notifyChanged()
    }

    private fun Page<T>.unpack() {
        entities.putAll(data.associateBy { it.url })
        if (startPage == null) startPage = this else morePages.add(this)
    }

    override fun get(url: String): T? = entities[url]
    override fun put(entity: T) = modify { entities[entity.url] = entity }
    override fun put(page: Page<T>) = modify { page.unpack() }
    override fun notifyChanged() {
        pageFlow.value = PageCollection(clock.now(), startPage, morePages)
    }
}
