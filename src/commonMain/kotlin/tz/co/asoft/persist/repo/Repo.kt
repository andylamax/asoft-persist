package tz.co.asoft.persist.repo

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.result.Result
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

abstract class Repo<T>(private val dao: Dao<T>) {

    open suspend fun filter(predicate: (T) -> Boolean) = dao.filter(predicate)

    open suspend fun create(list: List<T>) = dao.create(list)

    open suspend fun create(t: T) = dao.create(t)

    open suspend fun createCatching(list: List<T>) = Result.catching { create(list) }

    open suspend fun createCatching(t: T) = Result.catching { create(t) }

    open suspend fun edit(list: List<T>) = dao.edit(list)

    open suspend fun edit(t: T) = dao.edit(t)

    open suspend fun editCatching(list: List<T>) = Result.catching { edit(list) }

    open suspend fun editCatching(t: T) = Result.catching { edit(t) }

    open suspend fun delete(list: List<T>) = dao.delete(list)

    open suspend fun delete(t: T) = dao.delete(t)

    open suspend fun deleteCatching(list: List<T>) = Result.catching { delete(list) }

    open suspend fun deleteCatching(t: T) = Result.catching { delete(t) }

    open suspend fun load(ids: List<Any>) = dao.load(ids)

    open suspend fun load(id: Any) = dao.load(id)

    open suspend fun loadCatching(ids: List<Any>) = Result.catching { load(ids) }

    open suspend fun loadCatching(id: Any) = Result.catching { load(id) }

    open suspend fun observe(lifeCycle: LifeCycle, onChange: (List<T>?) -> Unit) = getLiveData().observe(lifeCycle, onChange)

    open suspend fun observeCatching(lifeCycle: LifeCycle, onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch { newLiveData.value = allCatching() }
        newLiveData.observe(lifeCycle, onChange)
    }

    open suspend fun observeForever(onChange: (List<T>?) -> Unit) = getLiveData().observeForever(onChange)

    open suspend fun observeForeverCatching(onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch { newLiveData.value = Result.catching { all() } }
        newLiveData.observeForever(onChange)
    }

    open suspend fun getLiveData(): LiveData<List<T>?> = coroutineScope {
        launch { liveData.value = all() }
        liveData
    }

    @Deprecated("Use getLiveData")
    open val allLive
        get() = dao.allLive

    open val liveData get() = dao.liveData

    open suspend fun all() = dao.all()

    open suspend fun allCatching() = dao.allCatching()

    suspend fun loadAll() = all()

    suspend fun loadAllCatching() = Result.catching { loadAll() }
}