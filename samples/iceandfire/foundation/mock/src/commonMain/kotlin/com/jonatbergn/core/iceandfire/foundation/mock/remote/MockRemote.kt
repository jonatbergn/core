package com.jonatbergn.core.iceandfire.foundation.mock.remote

import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.remote.Remote

class MockRemote<T : Entity>(
    var onGetOne: suspend (String) -> T = { throw NotImplementedError() },
    var onGetMany: suspend (String) -> Page<T> = { throw NotImplementedError() },
) : Remote<T> {
    override suspend fun getOne(url: String) = onGetOne(url)
    override suspend fun getMany(url: String) = onGetMany(url)
}
