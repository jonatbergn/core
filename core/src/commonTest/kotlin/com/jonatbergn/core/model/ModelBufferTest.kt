package com.jonatbergn.core.model

import com.jonatbergn.core.interact.UseCase
import com.jonatbergn.core.interact.reducer
import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@ExperimentalTime
class ModelBufferTest : BaseTest() {

    data class State(val value: String)
    data class Event(val value: String)
    data class Action(val value: String)

    private var count = 0
    private val buffer = 32_000
    private val model = Model(
        scope = CoroutineScope(Unconfined),
        initialState = State("init"),
        useCases = listOf(
            object : UseCase<Action> {
                override fun Flow<*>.filter() = filterIsInstance<Action>()

                override suspend fun interactOn(action: Action) = flow {
                    if (++count < buffer) {
                        delay(days(1))
                    } else {
                        emit(Event("buzz"))
                    }
                }
            }
        ),
        reducers = listOf(reducer<State, Event> { copy(value = it.value) }),
    )

    @Test
    fun buffer() = runTest {
        val states = launch { model.states.first { it.value == "buzz" } }
        repeat(buffer + 1) { model.actions.emit(Action("$it")) }
        states.join()
    }
}
