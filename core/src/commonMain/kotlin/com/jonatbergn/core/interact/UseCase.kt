package com.jonatbergn.core.interact

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
interface UseCase<Action> {

    fun Flow<*>.filter(): Flow<Action>

    suspend fun interactOn(action: Action): Flow<Any>

    companion object {
        fun <T> UseCase<T>.interactWith(actions: Flow<*>): Flow<Any> = actions
            .filter()
            .flatMapLatest(::interactOn)
    }
}
