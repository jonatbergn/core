package com.jonatbergn.core.iceandfire.foundation.mock.entity

import com.jonatbergn.core.iceandfire.foundation.entity.Entity

data class MockEntity(
    override val url: String,
) : Entity {
    companion object {
        fun fooEntity() = MockEntity("FOO")
        fun barEntity() = MockEntity("BAR")
    }
}
