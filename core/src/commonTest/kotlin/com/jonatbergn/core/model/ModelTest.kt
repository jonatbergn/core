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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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

    /**
     * A use case might emit effects immediately once they are hooked to the upstream
     * even without receiving an action from the upstream.
     *
     * Consider the following example:
     * ```
     * class MyUseCase : UseCase<Action>() {
     *   override fun Flow<*>.filter() = flowOf(Action())
     *   override suspend fun interactOn(action: Action) = flowOf(Effect())
     * }
     * ```
     * Eventually effects will be emitted before any collectors observe the downstream.
     * The model needs to replay all these effects and must make sure they do not get
     * lost.
     */
    @Test
    fun onHotUseCase_whichExecutesImmediately_StateGetsReducedCorrectly() = runTest {
        val initialState = "initial"
        val effects = (0..256).map { "effect$it" }
        val expectedState = (listOf(initialState) + effects).joinToString(",")
        Model(
            scope = CoroutineScope(Unconfined),
            initialState = initialState,
            useCases = listOf(object : UseCase<String> {
                override fun Flow<*>.filter() = flowOf("action")
                override suspend fun interactOn(action: String) = flowOf(*effects.toTypedArray())
            }),
            reducers = listOf(reducer<String, String> { "$this,$it" }),
        )
            .states
            // verify we get the most recent state
            .filter { it == expectedState }
            .first()
    }
}
