package tz.co.asoft.persist.lock

suspend fun <R> Lock.use(block: suspend () -> R): R {
    lock()
    val res = block()
    unlock()
    return res
}