package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.Dao

abstract class Repo<T>(private val dao: Dao<T>) {

    open val cache = mutableListOf<T>()

    open suspend fun filter(predicate: (T) -> Boolean) = dao.filter(predicate)

    open suspend fun create(t: T) = dao.create(t).also {
        it?.apply { cache.add(this) }
    }

    open suspend fun edit(t: T) = dao.edit(t)
    open suspend fun delete(t: T) = dao.delete(t)
    open suspend fun load(id: Any) = dao.load(id)

    @Deprecated(message = "Doesn't work well with more than one client", replaceWith = ReplaceWith("User all instead"))
    open suspend fun loadAll() = dao.loadAll()

    open suspend fun all() = dao.all().also {
        cache.clear()
        cache.addAll(it)
    }
}