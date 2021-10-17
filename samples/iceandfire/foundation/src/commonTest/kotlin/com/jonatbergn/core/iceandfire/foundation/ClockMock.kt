package com.jonatbergn.core.iceandfire.foundation

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TestTimeSource
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@OptIn(ExperimentalTime::class)
class ClockMock : Clock {
    private val timeSource: TestTimeSource = TestTimeSource()
    private val now = timeSource.markNow()
    operator fun plusAssign(duration: Duration) = timeSource.plusAssign(duration)
    override fun now() = Instant.fromEpochMilliseconds(now.elapsedNow().inWholeMilliseconds)
}
