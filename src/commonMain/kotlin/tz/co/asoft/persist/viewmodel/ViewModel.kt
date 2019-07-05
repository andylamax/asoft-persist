package tz.co.asoft.persist.viewmodel

import tz.co.asoft.persist.repo.Repo

abstract class ViewModel<T>(private val repo: Repo<T>) {
    open suspend fun filter(predicate: (T) -> Boolean) = repo.filter(predicate)
    open suspend fun create(t: T) = repo.create(t)
    open suspend fun edit(t: T) = repo.edit(t)
    open suspend fun delete(t: T) = repo.delete(t)
    open suspend fun load(id: Any) = repo.load(id)
    open suspend fun loadAll() = repo.loadAll()
}