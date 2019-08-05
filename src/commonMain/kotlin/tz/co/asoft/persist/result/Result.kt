package tz.co.asoft.persist.result

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import tz.co.asoft.persist.tools.Cause

@Serializable
class Result<out T>(val data: T? = null, val error: String? = null) {
    var status = error == null

    @Transient
    var cause: Cause? = error?.let { Cause(it) }

    companion object {
        fun <T> success(data: T) = Result(data)

        fun <T> failure(cause: Cause) = Result<T>(null, cause.message)

        suspend fun <T> catching(script: suspend () -> T?): Result<T> {
            var t: T? = null
            var cause: Cause? = null
            try {
                t = script()
            } catch (c: Cause) {
                cause = c
            }
            return Result(t, cause?.message).also {
                it.cause = cause
            }
        }
    }
}