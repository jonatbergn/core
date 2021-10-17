package com.jonatbergn.core.model

import com.jonatbergn.core.interact.UseCase
import com.jonatbergn.core.interact.reducer
import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

@ExperimentalTime
class ModelTest : BaseTest() {

    data class State(val value: String)
    data class Event(val value: String)
    data class Action(val value: String)

    private fun verifyStates(states: List<State>) {
        assertTrue { states.size == 2 }
        assertEquals("init,a", states[0].value)
        assertEquals("init,a,b", states[1].value)
    }

    @Test
    fun modelTest() = runTest {
        val statesOne = mutableListOf<State>()
        val statesTwo = mutableListOf<State>()
        val model = Model(
            scope = CoroutineScope(Unconfined),
            initialState = State("init"),
            useCases = listOf(
                object : UseCase<Action> {
                    override fun Flow<*>.filter() = filterIsInstance<Action>()
                    override suspend fun interactOn(action: Action) = flow {
                        emit(Event("a"))
                        delay(500)
                        emit(Event("b"))
                    }
                }
            ),
            reducers = listOf(reducer<State, Event> { copy(value = "${value},${it.value}") }),
        )

        val job1 = launch { model.states.take(2).toCollection(statesOne) }
        val job2 = launch { model.states.take(2).toCollection(statesTwo) }
        model.actions.emit(Action("action"))
        withTimeout(Duration.seconds(5)) { job1.join(); job2.join() }
        verifyStates(statesOne)
        verifyStates(statesTwo)

        val statesThree = mutableListOf<State>()
        val job3 = launch { model.states.take(1).toCollection(statesThree) }
        withTimeout(Duration.seconds(5)) { job3.join() }
        assertEquals(listOf(State("init,a,b")), statesThree)
    }
}
