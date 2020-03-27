package tz.co.asoft.persist.result

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import tz.co.asoft.persist.tools.Cause

@Serializable
@Deprecated("Use flows instead")
data class Result<T : Any>(val data: T? = null, var error: String? = null) {
    var status = error == null

    @Transient
    var cause: Cause? = error?.let { Cause(it) }

    fun respond(): T {
        if (data != null) {
            return data
        } else {
            val c = cause ?: Cause("Unknown Error")
            error = c.message
            throw c
        }
    }

    inline fun collect(action: (T) -> Unit): Result<T> {
        data?.let(action)
        return this
    }

    inline fun catch(action: (Cause) -> Unit): Result<T> {
        cause?.let(action)
        return this
    }

    companion object {
        fun <T : Any> success(data: T) = Result(data)

        fun <T : Any> failure(msg: String) = Result<T>(null, msg)

        fun <T : Any> failure(cause: Cause): Result<T> = failure(cause.message ?: "Unknown Error")
    }
}