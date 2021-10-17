package com.jonatbergn.core.exception

import com.jonatbergn.core.interact.UseCase
import kotlinx.coroutines.flow.Flow


private class UseCaseWithExceptionHandling<Action>(
    private val exceptionHandling: ExceptionHandling<Flow<Any>>,
    private val delegate: UseCase<Action>,
) : UseCase<Action> by delegate {

    override suspend fun interactOn(action: Action) =
        delegate.interactOn(action).handleExceptions(exceptionHandling)

}

fun <Action> UseCase<Action>.withExceptionHandling(
    builder: ExceptionHandlingBuilder<Flow<Any>>.() -> Unit,
): UseCase<Action> = UseCaseWithExceptionHandling(
    exceptionHandling = ExceptionHandlingBuilder<Flow<Any>>().apply(builder).build(),
    delegate = this,
)
