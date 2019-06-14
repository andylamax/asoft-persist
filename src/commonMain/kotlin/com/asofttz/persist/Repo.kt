package com.asofttz.persist

abstract class Repo<T>(private val dao: Dao<T>) {
    open suspend fun filter(predicate: (T) -> Boolean) = dao.filter(predicate)
    open suspend fun create(t: T) = dao.create(t)
    open suspend fun edit(t: T) = dao.edit(t)
    open suspend fun delete(t: T) = dao.delete(t)
    open suspend fun load(id: Any) = dao.load(id)
    open suspend fun loadAll() = dao.loadAll()
}