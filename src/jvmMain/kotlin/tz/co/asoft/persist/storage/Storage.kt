package tz.co.asoft.persist.storage

actual class Storage actual constructor(ctx: Any, name: String) {
    actual val name: String
        get() = throw Throwable("Implemented for JS (browser) and Android Only")

    actual suspend fun get(key: String): String? {
        throw Throwable("Implemented for JS (browser) and Android Only")
    }

    actual suspend fun set(key: String, value: String) {
        throw Throwable("Implemented for JS (browser) and Android Only")
    }

    actual suspend fun remove(key: String) {
        throw Throwable("Implemented for JS (browser) and Android Only")
    }

    actual suspend fun clear() {
        throw Throwable("Implemented for JS (browser) and Android Only")
    }
}