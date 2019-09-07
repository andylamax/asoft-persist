package tz.co.asoft.persist.viewmodel

import tz.co.asoft.persist.repo.Repo

abstract class ViewModel<T>(private val repo: Repo<T>) {

    open suspend fun filter(predicate: (T) -> Boolean) = repo.filter(predicate)

    open suspend fun create(list: List<T>) = repo.create(list)

    open suspend fun create(t: T) = repo.create(t)

    open suspend fun edit(list: List<T>) = repo.edit(list)

    open suspend fun edit(t: T) = repo.edit(t)

    open suspend fun delete(list: List<T>) = repo.delete(list)

    open suspend fun delete(t: T) = repo.delete(t)

    open suspend fun load(ids: List<Any>) = repo.load(ids)

    open suspend fun load(id: Any) = repo.load(id)

    open val allLive get() = repo.allLive

    open suspend fun all() = repo.all()
}