package com.jonatbergn.core.iceandfire.foundation.remote

import com.jonatbergn.core.iceandfire.foundation.entity.Page
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.mock.respondOk
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.time.ExperimentalTime

@ExperimentalTime
class RemoteTest {

    @Test
    fun testRemoteCalls() = runTest {
        val remote = RemoteImpl(
            HttpClient(MockEngine) {
                engine {
                    addHandler { request ->
                        when (request.url.toString()) {
                            "http://localhost/fox" -> respondOk("fox")
                            "http://localhost/bird" -> respondOk("bird")
                            "http://localhost/woopsie" -> respondError(Unauthorized, "")
                            else -> error("url not mocked")
                        }
                    }
                }
            },
            { "get-one/$it" },
            { listOf("get-many/$it") }
        )
        assertFails { remote.getOne("http://localhost/woopsie") }
        assertEquals("get-one/fox", remote.getOne("http://localhost/fox"))
        assertEquals(
            Page(
                "http://localhost/bird",
                null,
                persistentListOf("get-many/bird"),
            ),
            remote.getPage("http://localhost/bird")
        )
    }

}
