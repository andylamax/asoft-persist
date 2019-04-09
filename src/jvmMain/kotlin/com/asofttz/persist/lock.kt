package com.asofttz.persist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

actual suspend fun <T> Lockable.lock(pollTime: Long, run: suspend () -> T): T = synchronized(this) {
    runBlocking(Dispatchers.Unconfined) {
        run()
    }
}