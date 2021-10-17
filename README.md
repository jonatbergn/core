# Core

Classes for building [Kotlin Multiplatform Mobile](https://kotlinlang.org/lp/mobile/) apps based
on **unidirectional-data-flow** pattern
using [flows](https://kotlinlang.org/docs/reference/coroutines/flow.html#flows)
and [coroutines](https://kotlinlang.org/docs/reference/coroutines/basics.html).

## Architecture Overview

The framework exposes
a [Model](https://github.com/jonatbergn/core/blob/main/core/src/commonMain/kotlin/com/jonatbergn/core/model/Model.kt)
as its single API to be consumed by apps:

:arrow_upper_left: [publish actions](https://github.com/jonatbergn/core/blob/main/core/src/commonMain/kotlin/com/jonatbergn/core/model/Model.kt#L24)
:arrow_lower_right: [consume states](https://github.com/jonatbergn/core/blob/main/core/src/commonMain/kotlin/com/jonatbergn/core/model/Model.kt#L33)

## Sample App

The app lists game of throne houses using [anapioficeandfire](https://anapioficeandfire.com/).

**Run on browser**: `./gradlew iceandfireWeb`
**Install on android**: `./gradlew iceandfireAndroid`
