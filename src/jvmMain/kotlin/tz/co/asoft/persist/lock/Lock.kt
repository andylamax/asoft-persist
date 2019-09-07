package tz.co.asoft.persist.lock

import kotlinx.coroutines.delay

actual class Lock actual constructor(actual val pollTime: Long) {
    actual var isLocked: Boolean = false
        @Synchronized set
        @Synchronized get

    actual suspend fun lock() {
        if (!isLocked) {
            isLocked = true
            return
        }
        delay(pollTime)
        lock()
    }

    actual fun tryLock() = if (isLocked) {
        false
    } else {
        isLocked = true
        true
    }

    actual fun unlock() {
        isLocked = false
    }
}