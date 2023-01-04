package com.jonatbergn.core.iceandfire.app.api

class ApiDtoTest {

    private val decoded = ApiDto(
        books = "https://www.anapioficeandfire.com/api/books",
        characters = "https://www.anapioficeandfire.com/api/characters",
        houses = "https://www.anapioficeandfire.com/api/houses"
    )
    private val encoded = """
        {
          "books": "https://www.anapioficeandfire.com/api/books",
          "characters": "https://www.anapioficeandfire.com/api/characters",
          "houses": "https://www.anapioficeandfire.com/api/houses"
        }
    """.trimIndent()

//    @Test
    fun serialization() {
        //TODO
//        verifyJson(decoded, encoded) {
//            assertEquals("https://www.anapioficeandfire.com/api/books", books)
//            assertEquals("https://www.anapioficeandfire.com/api/characters", characters)
//            assertEquals("https://www.anapioficeandfire.com/api/houses", houses)
//        }
    }
}
