package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.model.Entity

open class EntityRepo<T : Entity>(private val dao: Dao<T>) : Repo<T>(dao) {
    protected val indexedCache = mutableMapOf<String, T>()
    override suspend fun create(t: T) = dao.create(t).also {
        it?.apply {
            cache.add(this)
            indexedCache[uid] = this
        }
    }
}