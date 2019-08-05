package tz.co.asoft.persist

import kotlinx.coroutines.*
import tz.co.asoft.persist.lock.Lock
import tz.co.asoft.persist.lock.use
import tz.co.asoft.test.asyncTest
import kotlin.coroutines.CoroutineContext
import kotlin.test.Test

class LockTest {
    private val lock1 = Lock()

    suspend fun runInLock(no: Int) {
        lock1.use {
            println("Block $no Started using lock")
            delay(1000L)
            println("Block $no Finished using lock")
            delay(1000L)
        }
    }

    @Test
    fun shouldLock() = asyncTest {
        coroutineScope {
            runInLock(1)
            runInLock(2)
            launch(Dispatchers.Default) { runInLock(3) }
            launch(Dispatchers.Unconfined) { runInLock(4) }
        }
    }
}