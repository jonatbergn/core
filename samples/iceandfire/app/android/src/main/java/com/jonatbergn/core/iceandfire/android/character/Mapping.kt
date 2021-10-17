package com.jonatbergn.core.iceandfire.android.character

import com.jonatbergn.core.iceandfire.app.character.Character

val Character?.nameText: String get() = this?.name.orEmpty()
