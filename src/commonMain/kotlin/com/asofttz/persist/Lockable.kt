package com.asofttz.persist

interface Lockable {
    var isRunning: Boolean
}

expect suspend fun <T> Lockable.lock(pollTime: Long = 10L,run: suspend () -> T): T