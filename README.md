# Core

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7f1c74dbec914bbd8f6840602faa5a91)](https://www.codacy.com/gh/jonatbergn/core/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=jonatbergn/core&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/7f1c74dbec914bbd8f6840602faa5a91)](https://www.codacy.com/gh/jonatbergn/core/dashboard?utm_source=github.com&utm_medium=referral&utm_content=jonatbergn/core&utm_campaign=Badge_Coverage)

Classes for building [Kotlin Multiplatform Mobile](https://kotlinlang.org/lp/mobile/) apps based
on **unidirectional-data-flow** pattern
using [flows](https://kotlinlang.org/docs/reference/coroutines/flow.html#flows)
and [coroutines](https://kotlinlang.org/docs/reference/coroutines/basics.html).

## Overview

### [Model](https://github.com/jonatbergn/core/blob/trunk/core/src/commonMain/kotlin/com/jonatbergn/core/model/Model.kt)

```kotlin
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
    private val events = useCases.map { it.interactWith(actions) }
        .merge()
        .shareIn(scope, Eagerly)

    val states: StateFlow<State> = events
        .scan(initialState) { s, event -> reducers.fold(s) { s1, reduce -> reduce(s1, event) } }
        .stateIn(scope, Eagerly, initialState)
}
```
:arrow_upper_left: [publish actions](https://github.com/jonatbergn/core/blob/trunk/core/src/commonMain/kotlin/com/jonatbergn/core/model/Model.kt#L24)
:arrow_lower_right: [consume states](https://github.com/jonatbergn/core/blob/trunk/core/src/commonMain/kotlin/com/jonatbergn/core/model/Model.kt#L33)

### [UseCase](https://github.com/jonatbergn/core/blob/trunk/core/src/commonMain/kotlin/com/jonatbergn/core/interact/UseCase.kt)

```kotlin
interface UseCase<Action> {

    fun Flow<*>.filter(): Flow<Action>

    suspend fun interactOn(action: Action): Flow<Any>

    companion object {
        fun <T> UseCase<T>.interactWith(actions: Flow<*>): Flow<Any> = actions
            .filter()
            .flatMapLatest(::interactOn)
    }
}
```
⚙️ [interact](https://github.com/jonatbergn/core/blob/trunk/core/src/commonMain/kotlin/com/jonatbergn/core/interact/UseCase.kt#L12) on received actions and emit events

### [Reducer](https://github.com/jonatbergn/core/blob/trunk/core/src/commonMain/kotlin/com/jonatbergn/core/interact/Reducer.kt)

```kotlin
nline fun <reified State, reified Event> reducer(
    crossinline reduce: State.(Event) -> State,
): (State, Any) -> State = { state, event -> if (event is Event) state.reduce(event) else state }
```

🔄 [reduce](https://github.com/jonatbergn/core/blob/trunk/core/src/commonMain/kotlin/com/jonatbergn/core/model/Model.kt#L34) state on each event

## Error handling 

For use case error handling check out [core-exceptions](https://github.com/jonatbergn/core/tree/trunk/core/exception)

## Sample App

The [app](https://github.com/jonatbergn/core/tree/trunk/samples/iceandfire/app) lists game of throne houses using [anapioficeandfire](https://anapioficeandfire.com/).

🕸️ **Run on browser**: `./gradlew iceandfireWeb`

🤖 **Install on android**: `./gradlew iceandfireAndroid`
