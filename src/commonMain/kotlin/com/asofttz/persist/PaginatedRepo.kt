package com.asofttz.persist

import kotlin.reflect.KClass

abstract class PaginatedRepo<T>(private val dao: PaginatedDao<T>) {
    open suspend fun filter(predicate: (T) -> Boolean) = dao.filter(predicate)
    open suspend fun create(t: T) = dao.create(t)
    open suspend fun edit(t: T) = dao.edit(t)
    open suspend fun delete(t: T) = dao.delete(t)
    open suspend fun load(id: Any) = dao.load(id)
    open suspend fun loadAll() = dao.loadAll()
    open suspend fun getMemory() = dao.getMemory()
    open suspend fun loadPage(pageNumber: Int) = dao.loadPage(pageNumber)
}

object PaginatedRepoFactory {
    val repos = mutableMapOf<KClass<*>, PaginatedRepo<*>>()

    inline fun <reified T> getRepo(dao: PaginatedDao<T>) = (repos.getOrPut(T::class) {
        object : PaginatedRepo<T>(dao) {}
    } as PaginatedRepo<T>)
}