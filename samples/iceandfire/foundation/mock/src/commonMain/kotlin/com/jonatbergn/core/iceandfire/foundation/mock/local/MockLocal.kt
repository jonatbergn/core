package com.jonatbergn.core.iceandfire.foundation.mock.local

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import com.jonatbergn.core.iceandfire.foundation.local.Local
import kotlinx.coroutines.flow.StateFlow

class MockLocal<T : Entity> : Local<T> {

    var notifyChangedInvocations = 0
        private set

    override val pageFlow: StateFlow<PageCollection<T>> = throw  NotImplementedError()
    override fun get(url: String) = throw  NotImplementedError()
    override fun put(entity: T) = throw  NotImplementedError()
    override fun put(page: Page<T>) = throw  NotImplementedError()
    override fun notifyChanged() {
        notifyChangedInvocations++
    }
}
