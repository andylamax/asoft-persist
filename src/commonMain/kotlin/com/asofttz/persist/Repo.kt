package com.asofttz.persist

import kotlin.reflect.KClass

abstract class Repo<T>(private val dao: Dao<T>) {
    suspend fun filter(predicate: (T) -> Boolean) = dao.filter(predicate)
    suspend fun create(t: T) = dao.create(t)
    suspend fun edit(t: T) = dao.edit(t)
    suspend fun delete(t: T) = dao.delete(t)
    suspend fun load(id: Int) = dao.load(id)
    suspend fun loadAll() = dao.loadAll()
}

object RepoFactory {
    val repos = mutableMapOf<KClass<*>, Repo<*>>()

    inline fun <reified T> getRepo(dao: Dao<T>) = (repos.getOrPut(T::class) {
        object : Repo<T>(dao) {}
    } as Repo<T>)
}