package com.jonatbergn.core.iceandfire.app

import com.jonatbergn.core.iceandfire.app.character.Character
import com.jonatbergn.core.iceandfire.app.house.House
import com.jonatbergn.core.iceandfire.foundation.entity.AbsFetchDependents
import com.jonatbergn.core.iceandfire.foundation.entity.Dependent
import com.jonatbergn.core.iceandfire.foundation.entity.Entity
import com.jonatbergn.core.iceandfire.foundation.local.Local
import com.jonatbergn.core.iceandfire.foundation.remote.Remote
import kotlinx.coroutines.Deferred

/**
 * Fetches selected [Dependent]s for a given type of [Entity]
 *
 * @param characters the selector for depending [Character]s which should be fetched
 * @param houses the selector for depending [House]s which should be fetched
 */
data class FetchDependentsImpl<T : Entity>(
    private val remoteCharacters: Remote<Character>,
    private val remoteHouses: Remote<House>,
    private val localCharacters: Local<Character>,
    private val localHouses: Local<House>,
    val characters: T.() -> List<Dependent<Character>> = { emptyList() },
    val houses: T.() -> List<Dependent<House>> = { emptyList() },
) : AbsFetchDependents<T>(
    localCharacters,
    localHouses
) {
    override suspend fun invoke(p1: T): List<Deferred<Unit>> = listOf(
        p1.houses().map { fetchAsync(it, localHouses, remoteHouses) },
        p1.characters().map { fetchAsync(it, localCharacters, remoteCharacters) }
    ).flatten()
}
