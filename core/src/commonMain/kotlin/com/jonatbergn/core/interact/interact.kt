package com.jonatbergn.core.interact

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

suspend operator fun <State> MutableStateFlow<State>.invoke(reduce: suspend State.() -> State) = update { reduce(it) }
