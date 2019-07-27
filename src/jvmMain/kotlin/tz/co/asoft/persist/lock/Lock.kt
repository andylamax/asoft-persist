package tz.co.asoft.persist.lock

import kotlinx.coroutines.delay
import java.util.concurrent.locks.ReentrantLock

actual class Lock actual constructor(actual val pollTime: Long) {
    private val javaLock = ReentrantLock()
    actual var isLocked: Boolean = false
        @Synchronized set(value) {
            field = value
        }

    actual suspend fun lock() {
        if(!isLocked) {
            isLocked = true
            return
        }
        delay(pollTime)
        lock()
    }

    actual fun tryLock() = if(isLocked) {
        false
    } else {
        isLocked = true
        true
    }

    actual fun unlock() {
        isLocked = false
    }
}