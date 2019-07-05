package tz.co.asoft.persist

import kotlinx.coroutines.delay
import tz.co.asoft.persist.tools.Lockable
import tz.co.asoft.persist.tools.lock
import kotlin.test.Test

class LockTest {
    object TestObj : Lockable {
        override var isRunning = false

        suspend fun testFun() = lock {
            delay(1000)
            println("Done delaying")
        }
    }

    @Test
    fun shouldLock() {

    }
}