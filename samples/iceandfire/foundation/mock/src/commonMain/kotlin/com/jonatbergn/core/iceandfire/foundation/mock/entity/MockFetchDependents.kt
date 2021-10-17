package com.jonatbergn.core.iceandfire.foundation.mock.entity

import com.jonatbergn.core.iceandfire.foundation.entity.AbsFetchDependents
import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.local.Local
import kotlinx.coroutines.Deferred

class MockFetchDependents<T : Entity>(
    vararg locals: Local<*>,
    val select: suspend AbsFetchDependents<T>.(T) -> List<Deferred<Unit>>,
) : AbsFetchDependents<T>(*locals) {
    override suspend fun invoke(p1: T) = select(p1)
}
