package com.jonatbergn.core.iceandfire.foundation.remote

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Page

class FakeRemote<T : Entity>(
    var onGetOne: suspend (String) -> T = { throw NotImplementedError() },
    var onGetMany: suspend (String) -> Page<T> = { throw NotImplementedError() },
) : Remote<T> {
    override suspend fun getOne(url: String) = onGetOne(url)
    override suspend fun getPage(url: String) = onGetMany(url)
}
