package tz.co.asoft.persist.repo

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.result.Result
import tz.co.asoft.rx.lifecycle.ILifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

open class Repo<T>(private val dao: IDao<T>) : IRepo<T> {

    override suspend fun filter(predicate: (T) -> Boolean) = dao.filter(predicate)

    override suspend fun create(list: List<T>) = dao.create(list)

    override suspend fun create(t: T) = dao.create(t)

    override suspend fun createCatching(list: List<T>) = Result.catching { create(list) }

    override suspend fun createCatching(t: T) = Result.catching { create(t) }

    override suspend fun edit(list: List<T>) = dao.edit(list)

    override suspend fun edit(t: T) = dao.edit(t)

    override suspend fun editCatching(list: List<T>) = Result.catching { edit(list) }

    override suspend fun editCatching(t: T) = Result.catching { edit(t) }

    override suspend fun delete(list: List<T>) = dao.delete(list)

    override suspend fun delete(t: T) = dao.delete(t)

    override suspend fun deleteCatching(list: List<T>) = Result.catching { delete(list) }

    override suspend fun deleteCatching(t: T) = Result.catching { delete(t) }

    override suspend fun load(ids: List<Any>) = dao.load(ids)

    override suspend fun load(id: Any) = dao.load(id)

    override suspend fun loadCatching(ids: List<Any>) = Result.catching { load(ids) }

    override suspend fun loadCatching(id: Any) = Result.catching { load(id) }

    override suspend fun observe(lifeCycle: ILifeCycle, onChange: (List<T>?) -> Unit) = getLiveData().observe(lifeCycle, onChange)

    override suspend fun observeCatching(lifeCycle: ILifeCycle, onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch {
            val res = allCatching()
            res.data?.let { liveData.value = it }
            res.cause?.let { if (liveData.value == null) newLiveData.value = res }
        }
        newLiveData.observe(lifeCycle, onChange)
    }

    override suspend fun observeForever(onChange: (List<T>?) -> Unit) = getLiveData().observeForever(onChange)

    override suspend fun observeForeverCatching(onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch {
            val res = allCatching()
            res.data?.let { liveData.value = it }
            res.cause?.let { if (liveData.value == null) newLiveData.value = res }
        }
        newLiveData.observeForever(onChange)
    }

    override suspend fun getLiveData(): LiveData<List<T>?> = coroutineScope {
        launch { liveData.value = all() }
        liveData
    }

    override val liveData get() = dao.liveData

    override suspend fun all() = dao.all()

    override suspend fun allCatching() = dao.allCatching()

    override suspend fun loadAll() = all()

    override suspend fun loadAllCatching() = Result.catching { loadAll() }
}