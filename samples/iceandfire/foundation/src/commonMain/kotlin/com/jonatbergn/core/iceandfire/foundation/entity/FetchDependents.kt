package com.jonatbergn.core.iceandfire.foundation.entity

import kotlinx.coroutines.Deferred

/**
 * Logic to fetch [Dependent] entity data
 */
interface FetchDependents<T : Entity> {
    /**
     * @param p1 the entity for wich [Dependent] data should be fetched
     * @return a list of [Deferred] which need to be awaited for fetching the depending entities.
     */
    suspend operator fun invoke(p1: T): List<Deferred<Unit>>
}
