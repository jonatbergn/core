package com.jonatbergn.core.iceandfire.foundation.entity

import kotlin.jvm.JvmInline

/**
 * A resource identified b an url
 */
interface Entity {

    val url: String

    @JvmInline
    value class Pointer<T : Entity>(
        val url: String,
    ) {

        companion object Factory {

            operator fun <T : Entity> invoke(
                url: String?,
            ): Pointer<T>? {
                return Pointer(url.takeUnless { it.isNullOrBlank() } ?: return null)
            }

            operator fun <T : Entity> invoke(
                urls: List<String>?,
            ): List<Pointer<T>>? = urls?.mapNotNull { Factory(it) }
        }
    }

    companion object {
        val <T : Entity> T.pointer get() = Pointer<T>(url)
    }
}
