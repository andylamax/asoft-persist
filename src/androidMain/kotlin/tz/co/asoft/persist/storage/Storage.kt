package tz.co.asoft.persist.storage

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tz.co.asoft.platform.core.Ctx

actual class Storage actual constructor(ctx: Ctx, actual val name: String) {
    private val db = ctx.getSharedPreferences(name, Context.MODE_PRIVATE)

    actual suspend fun get(key: String): String? = withContext(Dispatchers.IO) {
        db.getString(key, null)
    }

    actual suspend fun set(key: String, value: String) = withContext(Dispatchers.IO) {
        db.edit().putString(key, value).apply()
    }

    actual suspend fun remove(key: String) = withContext(Dispatchers.IO){
        db.edit().remove(key).apply()
    }

    actual suspend fun clear() = withContext(Dispatchers.IO){
        db.edit().clear().apply()
    }
}