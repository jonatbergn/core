package com.jonatbergn.core.iceandfire.app.character

import com.jonatbergn.core.iceandfire.foundation.entity.Entity.Pointer

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
        father = Pointer(father),
        mother = Pointer(mother),
        spouse = Pointer(spouse),
        allegiances = Pointer(allegiances),
    )

val List<CharacterDto>.asCharacterList
    get() = map(CharacterDto::asCharacter)
