package tz.co.asoft.persist.result

import tz.co.asoft.persist.tools.Cause

inline fun <T : Any> catching(block: () -> T?): Result<T> = try {
    Result(block())
} catch (c: Cause) {
    Result.failure(c)
}

