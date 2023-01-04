package com.jonatbergn.core.iceandfire.foundation.mock.entity

import com.jonatbergn.core.iceandfire.foundation.entity.Page
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity.Companion.barEntity
import com.jonatbergn.core.iceandfire.foundation.mock.entity.MockEntity.Companion.fooEntity
import kotlinx.collections.immutable.persistentListOf

object MockPages {

    val SECOND_PAGE = Page("SECOND_PAGE", null, persistentListOf(barEntity()))
    val FIRST_PAGE = Page("FIRST_PAGE", SECOND_PAGE.url, persistentListOf(fooEntity()))
}
