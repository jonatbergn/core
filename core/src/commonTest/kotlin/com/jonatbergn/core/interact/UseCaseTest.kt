package com.jonatbergn.core.interact

import com.jonatbergn.core.interact.UseCase.Companion.interactWith
import com.jonatbergn.core.test.BaseTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList

@ExperimentalTime
class UseCaseTest : BaseTest() {

    @Test
    fun filter() = runTest {
        val actions = (0..4).map(Int::toString)
        val moreActions = (5..9).map(Int::toString)
        val events = object : UseCase<String> {
            override fun Flow<*>.filter() = filterIsInstance<String>().filter { it in actions }
            override suspend fun interactOn(action: String) = flowOf(action.toInt())
        }.interactWith((actions + moreActions).asFlow()).toList()
        assertEquals((0..4).toList(), events)
    }

    @Test
    fun interactOn() = runTest {
        val actions = (0..4).map(Int::toString)
        val events = object : UseCase<String> {
            override fun Flow<*>.filter() = filterIsInstance<String>()
            override suspend fun interactOn(action: String) = flowOf("!$action")
        }.interactWith(actions.asFlow()).toList()
        assertEquals(actions.map { "!$it" }, events)
    }
}
