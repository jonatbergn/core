package com.jonatbergn.core.iceandfire.foundation.mock.repo

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.PageCollection
import com.jonatbergn.core.iceandfire.foundation.entity.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MockRepo<T : Entity>(
    private val pages: Flow<PageCollection<T>> = emptyFlow(),
) : Repo<T> {

    var fetchNextPageInvocations = 0
        private set
    var fetchOneInvocations = emptyList<String>()
        private set

    override fun observePages() = pages

    override suspend fun fetchNextPage() {
        fetchNextPageInvocations++
    }

    override suspend fun fetchOne(url: String) {
        fetchOneInvocations = fetchOneInvocations + url
    }
}
