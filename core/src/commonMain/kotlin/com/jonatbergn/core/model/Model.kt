package com.jonatbergn.core.model

import com.jonatbergn.core.interact.UseCase
import com.jonatbergn.core.interact.UseCase.Companion.interactWith
import kotlin.Int.Companion.MAX_VALUE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class Model<State>(
    scope: CoroutineScope,
    initialState: State,
    useCases: List<UseCase<*>>,
    reducers: List<(State, Any) -> State>,
) {

    val actions: MutableSharedFlow<Any> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = DROP_OLDEST
    )
    private val effects = useCases.map { it.interactWith(actions) }
        .merge()
        .shareIn(scope, Eagerly, MAX_VALUE)

    val states: StateFlow<State> = effects
        .scan(initialState) { s, event -> reducers.fold(s) { s1, reduce -> reduce(s1, event) } }
        .stateIn(scope, Eagerly, initialState)
}
