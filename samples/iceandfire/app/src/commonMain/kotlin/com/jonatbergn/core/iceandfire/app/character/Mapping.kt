package com.jonatbergn.core.iceandfire.app.character

import com.jonatbergn.core.iceandfire.foundation.entity.Dependent.Companion.asDependent
import com.jonatbergn.core.iceandfire.foundation.entity.Dependent.Companion.asDependents

val CharacterDto.asCharacter: Character
    get() = Character(
        url = url,
        name = name,
        gender = gender,
        culture = culture,
        born = born,
        died = died,
        titles = titles,
        aliases = aliases,
        books = books,
        povBooks = povBooks,
        tvSeries = tvSeries,
        playedBy = playedBy,
        father = father.asDependent(),
        mother = mother.asDependent(),
        spouse = spouse.asDependent(),
        allegiances = allegiances.asDependents(),
    )

val List<CharacterDto>.asCharacterList
    get() = map(CharacterDto::asCharacter)
