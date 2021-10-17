package com.jonatbergn.core.interact

import com.jonatbergn.core.interact.UseCase.Companion.interactWith
import kotlin.test.Test
import kotlin.test.assertSame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf

class UseCaseFilterTest {

    @Test
    fun filterGetsAppliedOnUpstream() {
        val upstream = flowOf(Any())
        var recordedUpstream: Flow<*>? = null

        object : UseCase<Any> {
            override fun Flow<*>.filter(): Flow<Any> {
                recordedUpstream = this
                return filterIsInstance()
            }

            override suspend fun interactOn(action: Any) = emptyFlow<Any>()

        }.interactWith(upstream)
        assertSame(upstream, recordedUpstream)
    }
}
