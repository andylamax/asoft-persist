package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.model.Entity

open class EntityRepo<T : Entity>(private val dao: Dao<T>) : Repo<T>(dao) {
    override suspend fun create(list: List<T>): List<T>? = dao.create(list)?.also {
        allLive.value.addAll(it)
    }
}