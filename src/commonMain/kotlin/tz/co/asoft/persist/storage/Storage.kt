package tz.co.asoft.persist.storage

import tz.co.asoft.platform.Ctx

expect class Storage(ctx: Ctx, name: String) {
    val name: String
    suspend fun get(key: String): String?
    suspend fun set(key: String, value: String)
    suspend fun remove(key: String)
    suspend fun clear()
}