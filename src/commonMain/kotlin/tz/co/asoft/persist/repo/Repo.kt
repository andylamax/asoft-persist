package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.rx.lifecycle.LifeCycle

abstract class Repo<T>(private val dao: Dao<T>) {

    open suspend fun filter(predicate: (T) -> Boolean) = dao.filter(predicate)

    open suspend fun create(list: List<T>) = dao.create(list)

    open suspend fun create(t: T) = create(listOf(t))?.first()

    open suspend fun edit(list: List<T>) = dao.edit(list)

    open suspend fun edit(t: T) = dao.edit(t)

    open suspend fun delete(list: List<T>) = dao.delete(list)

    open suspend fun delete(t: T) = dao.delete(t)

    open suspend fun load(ids: List<Any>) = dao.load(ids)

    open suspend fun load(id: Any) = dao.load(id)

    open fun observe(lifeCycle: LifeCycle, onChange: (List<T>?) -> Unit) = dao.observe(lifeCycle, onChange)

    open fun observeForever(onChange: (List<T>?) -> Unit) = dao.observeForever(onChange)

    open suspend fun getLiveData() = dao.getLiveData()

    open val allLive get() = dao.allLive

    open suspend fun all() = dao.all()
}