package com.jonatbergn.core.iceandfire.foundation.remote

import com.jonatbergn.core.iceandfire.foundation.entity.Page
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.isSuccess

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

    private suspend fun <Result> get(
        url: String,
        block: suspend (Headers, String) -> Result,
    ): Result = client.get(url).run {
        if (status.isSuccess()) {
            block(headers, bodyAsText())
        } else {
            error("Failed to GET $this")
        }
    }

    override suspend fun getOne(url: String) = get(url) { _, body -> decodeOne(body) }

    override suspend fun getPage(url: String) = get(url) { headers, body ->
        Page(url = url, next = headers.link().next, data = decodeMany(body))
    }
}
