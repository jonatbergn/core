package com.jonatbergn.core.iceandfire.app.character

import kotlin.test.Test

class CharacterDtoTest {

    private val encoded = """
        {
          "url": "https://www.anapioficeandfire.com/api/characters/1",
          "name": "",
          "gender": "Female",
          "culture": "Braavosi",
          "born": "",
          "died": "",
          "titles": [
            ""
          ],
          "aliases": [
            "The Daughter of the Dusk"
          ],
          "father": "",
          "mother": "",
          "spouse": "",
          "allegiances": [],
          "books": [
            "https://www.anapioficeandfire.com/api/books/5"
          ],
          "povBooks": [],
          "tvSeries": [
            ""
          ],
          "playedBy": [
            ""
          ]
        }
    """.trimIndent()
    private val decoded = CharacterDto(
        url = "https://www.anapioficeandfire.com/api/characters/1",
        name = "",
        gender = "Female",
        culture = "Braavosi",
        born = "",
        died = "",
        titles = listOf(""),
        aliases = listOf("The Daughter of the Dusk"),
        father = "",
        mother = "",
        spouse = "",
        allegiances = emptyList(),
        books = listOf("https://www.anapioficeandfire.com/api/books/5"),
        povBooks = emptyList(),
        tvSeries = listOf(""),
        playedBy = listOf(""),
    )

    @Test
    fun serialization() {
        //TODO
//        verifyJson(decoded, encoded) {
//            assertEquals("https://www.anapioficeandfire.com/api/characters/1", url)
//            assertEquals("", name)
//            assertEquals("Female", gender)
//            assertEquals("Braavosi", culture)
//            assertEquals("", born)
//            assertEquals("", died)
//            assertEquals(listOf(""), titles)
//            assertEquals(listOf("The Daughter of the Dusk"), aliases)
//            assertEquals("", father)
//            assertEquals("", mother)
//            assertEquals("", spouse)
//            assertEquals(emptyList(), allegiances)
//            assertEquals(listOf("https://www.anapioficeandfire.com/api/books/5"), books)
//            assertEquals(emptyList(), povBooks)
//            assertEquals(listOf(""), tvSeries)
//            assertEquals(listOf(""), playedBy)
//        }
    }
}
