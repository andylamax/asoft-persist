package tz.co.asoft.persist.tools

import kotlinx.coroutines.*
import kotlin.jvm.Synchronized

@Synchronized
@Deprecated("Do not use this", ReplaceWith("Replace with fun Lock.use()"))
actual suspend fun <T> Lockable.lock(pollTime: Long, run: suspend () -> T): T = coroutineScope {
    run()
}