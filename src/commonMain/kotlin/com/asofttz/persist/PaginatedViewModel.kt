package com.asofttz.persist

abstract class PaginatedViewModel<T>(private val repo: PaginatedRepo<T>) {
    open suspend fun filter(predicate: (T) -> Boolean) = repo.filter(predicate)
    open suspend fun create(t: T) = repo.create(t)
    open suspend fun edit(t: T) = repo.edit(t)
    open suspend fun delete(t: T) = repo.delete(t)
    open suspend fun load(id: Any) = repo.load(id)
    open suspend fun loadAll() = repo.loadAll()
    open suspend fun getMemory() = repo.getMemory()
    open suspend fun loadPage(pageNumber: Int) = repo.loadPage(pageNumber)
}