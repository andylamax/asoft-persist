package tz.co.asoft.persist.storage

expect class Storage(ctx: Any,name: String) {
    val name: String
    suspend fun get(key: String) : String?
    suspend fun set(key: String,value: String)
    suspend fun remove(key: String)
    suspend fun clear()
}