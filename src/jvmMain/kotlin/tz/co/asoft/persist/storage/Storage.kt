package tz.co.asoft.persist.storage

import tz.co.asoft.platform.core.Ctx

actual class Storage actual constructor(ctx: Ctx, actual val name: String) {
    actual suspend fun get(key: String): String? = null

    actual suspend fun set(key: String, value: String) {

    }

    actual suspend fun remove(key: String) {

    }

    actual suspend fun clear() {

    }
}