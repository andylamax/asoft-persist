package tz.co.asoft.persist.tools

import kotlinx.coroutines.coroutineScope
import kotlin.jvm.Synchronized

@Synchronized
actual suspend fun <T> Lockable.lock(pollTime: Long, run: suspend () -> T): T = coroutineScope {
    run()
}