package com.jonatbergn.core.iceandfire.foundation.entity

import kotlinx.coroutines.flow.Flow

interface Repo<T : Entity> {

    fun observePages(): Flow<PageCollection<T>>
    suspend fun fetchNextPage()
    suspend fun fetchOne(url: String)
}
