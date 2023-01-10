package com.jonatbergn.core.interact

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

operator fun <State> MutableStateFlow<State>.invoke(reduce: State.() -> State) = update(reduce)
