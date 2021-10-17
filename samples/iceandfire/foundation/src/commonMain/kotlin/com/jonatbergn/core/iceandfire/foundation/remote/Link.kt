package com.jonatbergn.core.iceandfire.foundation.remote

import io.ktor.http.Headers

/**
 * A data class which contains parsed link data.
 */
data class Link(
    val next: String?,
)

private fun String?.parse(rel: String): String? {
    if (this == null) return null
    val match = """<(.*)>; rel="$rel"""".toRegex().find(this) ?: return null
    val (link) = match.destructured
    return link
}

/**
 * Parses the "link" header
 *
 * @return [Link] providing the link properties
 */
fun Headers.link() = this["link"].run { Link(next = parse("next")) }
