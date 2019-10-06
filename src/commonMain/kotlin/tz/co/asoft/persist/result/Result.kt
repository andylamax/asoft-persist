package tz.co.asoft.persist.result

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import tz.co.asoft.persist.tools.Cause

@Serializable
class Result<out T>(val data: T? = null, var error: String? = null) {
    var status = error == null

    @Transient
    var cause: Cause? = error?.let { Cause(it) }

    fun respond(): T {
        if (data != null) {
            return data
        } else {
            val c = cause
            if (c != null) {
                throw c
            } else {
                error = "Unknown Error"
                throw Cause(error)
            }
        }
    }

    companion object {
        fun <T> success(data: T) = Result(data)

        fun <T> failure(msg: String): Result<T> = failure(Cause(msg))

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