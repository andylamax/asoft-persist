package tz.co.asoft.persist.storage

import kotlinx.coroutines.*
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.browser.window

actual class Storage actual constructor(ctx: Any, actual val name: String) {
    private val db = window.localStorage

    private fun getTable(): dynamic {
        var table = db[name]
        if (table == null) {
            table = "{}"
        }
        return JSON.parse(table)
    }

    actual suspend fun get(key: String): String? = withContext(Dispatchers.Unconfined) {
        getTable()[key]
    }

    actual suspend fun set(key: String, value: String) {
        GlobalScope.launch(Dispatchers.Unconfined) {
            val table = getTable()
            table[key] = value
            db[name] = JSON.stringify(table)
        }
    }

    actual suspend fun remove(key: String) = withContext(Dispatchers.Unconfined) {
        val table = getTable()
        table[key] = undefined
        db[name] = JSON.stringify(table)
    }

    actual suspend fun clear() = withContext(Dispatchers.Unconfined) { db.removeItem(name) }
}