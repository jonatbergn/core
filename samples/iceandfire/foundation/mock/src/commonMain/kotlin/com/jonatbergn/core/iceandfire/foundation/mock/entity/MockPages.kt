package com.jonatbergn.core.iceandfire.foundation.mock.entity

import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity.Companion.barEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity.Companion.fooEntity

object MockPages {

    val SECOND_PAGE = Page("SECOND_PAGE", listOf(barEntity()), null)
    val FIRST_PAGE = Page("FIRST_PAGE", listOf(fooEntity()), SECOND_PAGE.url)
}
