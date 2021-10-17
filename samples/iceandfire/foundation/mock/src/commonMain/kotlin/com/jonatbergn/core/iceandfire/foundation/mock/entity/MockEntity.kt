package com.jonatbergn.core.iceandfire.foundation.mock.entity

import com.jonatbergn.core.iceandfire.foundation.entity.Dependent
import com.jonatbergn.core.iceandfire.foundation.entity.Entity

data class MockEntity(
    override val url: String,
    val dependent: Dependent<MockEntity>,
) : Entity {
    companion object {
        fun fooEntity() = MockEntity("FOO", Dependent(null))
        fun barEntity() = MockEntity("BAR", Dependent("FOO"))
    }
}
