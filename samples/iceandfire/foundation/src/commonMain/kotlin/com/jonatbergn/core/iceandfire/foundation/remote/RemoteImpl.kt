package com.jonatbergn.core.iceandfire.foundation.remote

import com.jonatbergn.core.iceandfire.foundation.entity.Page
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.isSuccess
import kotlinx.collections.immutable.toImmutableList

/**
 * An implementation of [Remote] using [HttpClient] to retrieve resources.
 *
 * @param client the client used for this repo
 * @param decodeOne closure used to decode a [String] to [T]\
 * @param decodeMany closure used to decode a [List] of [Strings] to [List] of [T]
 */
class RemoteImpl<T>(
    private val client: HttpClient,
    private val decodeOne: (String) -> T,
    private val decodeMany: (String) -> List<T>,
) : Remote<T> {

    private suspend operator fun <T> String.invoke(block: suspend String.(Headers) -> T): T =
        client.get(this).run {
            if (status.isSuccess()) bodyAsText().block(headers) else error("Failed to GET $this")
        }

    override suspend fun getOne(url: String) = url {
        decodeOne(this)
    }

    override suspend fun getPage(url: String) = url {
        Page(url, it.link().next, decodeMany(this).toImmutableList())
    }
}
