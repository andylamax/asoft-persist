package com.asofttz.persist

import kotlin.reflect.KClass

abstract class Repo<T>(private val dao: Dao<T>) {
    open suspend fun filter(predicate: (T) -> Boolean) = dao.filter(predicate)
    open suspend fun create(t: T) = dao.create(t)
    open suspend fun edit(t: T) = dao.edit(t)
    open suspend fun delete(t: T) = dao.delete(t)
    open suspend fun load(id: Int) = dao.load(id)
    open suspend fun loadAll() = dao.loadAll()
}

object RepoFactory {
    val repos = mutableMapOf<KClass<*>, Repo<*>>()

    inline fun <reified T> getRepo(dao: Dao<T>) = (repos.getOrPut(T::class) {
        object : Repo<T>(dao) {}
    } as Repo<T>)
}