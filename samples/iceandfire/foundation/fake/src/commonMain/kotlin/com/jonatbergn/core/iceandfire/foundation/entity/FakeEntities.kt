package com.jonatbergn.core.iceandfire.foundation.entity

import kotlin.jvm.JvmInline

object FakeEntities {

    @JvmInline
    private value class FakeEntity(override val url: String) : Entity

    private val entity01: Entity = FakeEntity("entity01")
    private val entity02: Entity = FakeEntity("entity02")
    private val entity03: Entity = FakeEntity("entity03")
    private val entity04: Entity = FakeEntity("entity04")
    private val entity05: Entity = FakeEntity("entity05")
    private val entity06: Entity = FakeEntity("entity06")

    val page03 = Page(
        url = "page03", next = null, data = listOf(
            entity05,
            entity06,
        )
    )
    val page02 = Page(
        url = "page02", next = page03.url, data = listOf(
            entity03,
            entity04,
        )
    )
    val page01 = Page(
        url = "page01", next = page02.url, listOf(
            entity01,
            entity02,
        )
    )

    val pages = listOf(page01, page02, page03)
}
