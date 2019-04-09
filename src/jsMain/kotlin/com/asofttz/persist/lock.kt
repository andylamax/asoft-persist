package com.asofttz.persist

import kotlinx.coroutines.delay

actual suspend fun <T> Lockable.lock(pollTime: Long, run: suspend () -> T): T {
    return if (!isRunning) {
        isRunning = true
        val t = run()
        isRunning = false
        t
    } else {
        delay(pollTime)
        return lock(pollTime, run)
    }
}