package tz.co.asoft.persist.viewmodel

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

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

    open fun observe(lifeCycle: LifeCycle, onChange: (List<T>?) -> Unit) = repo.observe(lifeCycle, onChange)

    open fun observeForever(onChange: (List<T>?) -> Unit) = repo.observeForever(onChange)

    open suspend fun getLiveData() = repo.getLiveData()

    open val allLive get() = repo.allLive

    open suspend fun all() = repo.all()
}