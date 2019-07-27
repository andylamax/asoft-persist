package tz.co.asoft.persist.lock

expect class Lock(pollTime: Long = 100L) {
    val pollTime: Long
    var isLocked : Boolean
    suspend fun lock()
    fun tryLock() : Boolean
    fun unlock()
}