package com.jonatbergn.core.interact

inline fun <reified State, reified Event> reducer(
    crossinline reduce: State.(Event) -> State,
): (State, Any) -> State = { state, event -> if (event is Event) state.reduce(event) else state }
