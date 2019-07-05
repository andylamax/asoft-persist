package tz.co.asoft.persist.tools

interface Lockable {
    var isRunning: Boolean
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect suspend fun <T> Lockable.lock(pollTime: Long = 10L, run: suspend () -> T): T