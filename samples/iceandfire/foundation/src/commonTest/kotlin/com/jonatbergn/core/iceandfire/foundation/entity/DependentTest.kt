package com.jonatbergn.core.iceandfire.foundation.entity

import com.jonatbergn.core.iceandfire.foundation.entity.Dependent.Companion.asDependent
import com.jonatbergn.core.iceandfire.foundation.entity.Dependent.Companion.asDependents
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity
import kotlin.test.Test
import kotlin.test.assertEquals

class DependentTest {

    @Test
    fun singleStringToDependent() {
        "url-a".run { assertEquals(this, asDependent<MockEntity>().url) }
    }

    @Test
    fun manyStringToDependent() {
        val urls = (0..9).map { "url-$it" }
        assertEquals(urls, urls.asDependents<MockEntity>().map { it.url })
    }

    @Test
    fun manyStringToDependentNull() {
        val urls: List<String>? = null
        assertEquals(emptyList(), urls.asDependents<MockEntity>().map { it.url })
    }
}
