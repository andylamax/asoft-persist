package tz.co.asoft.persist.viewmodel

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.persist.repo.IRepo
import tz.co.asoft.persist.result.Result
import tz.co.asoft.rx.lifecycle.ILifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

open class ViewModel<T>(private val repo: IRepo<T>) {

    open suspend fun filter(predicate: (T) -> Boolean) = repo.filter(predicate)

    open suspend fun create(list: List<T>) = repo.create(list)

    open suspend fun create(t: T) = repo.create(t)

    open suspend fun createCatching(list: List<T>) = Result.catching { create(list) }

    open suspend fun createCatching(t: T) = Result.catching { create(t) }

    open suspend fun edit(list: List<T>) = repo.edit(list)

    open suspend fun edit(t: T) = repo.edit(t)

    open suspend fun editCatching(list: List<T>) = Result.catching { edit(list) }

    open suspend fun editCatching(t: T) = Result.catching { edit(t) }

    open suspend fun delete(list: List<T>) = repo.delete(list)

    open suspend fun delete(t: T) = repo.delete(t)

    open suspend fun deleteCatching(list: List<T>) = Result.catching { delete(list) }

    open suspend fun deleteCatching(t: T) = Result.catching { delete(t) }

    open suspend fun load(ids: List<Any>) = repo.load(ids)

    open suspend fun load(id: Any) = repo.load(id)

    open suspend fun loadCatching(ids: List<Any>) = Result.catching { load(ids) }

    open suspend fun loadCatching(id: Any) = Result.catching { load(id) }

    open suspend fun observe(lifeCycle: ILifeCycle, onChange: (List<T>?) -> Unit) = getLiveData().observe(lifeCycle, onChange)

    open suspend fun observeCatching(lifeCycle: ILifeCycle, onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch {
            val res = allCatching()
            res.data?.let { liveData.value = it }
            res.cause?.let { if (liveData.value == null) newLiveData.value = res }
        }
        newLiveData.observe(lifeCycle, onChange)
    }

    open suspend fun observeForever(onChange: (List<T>?) -> Unit) = getLiveData().observeForever(onChange)

    open suspend fun observeForeverCatching(onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch {
            val res = allCatching()
            res.data?.let { liveData.value = it }
            res.cause?.let { if (liveData.value == null) newLiveData.value = res }
        }
        newLiveData.observeForever(onChange)
    }

    open suspend fun getLiveData(): LiveData<List<T>?> = coroutineScope {
        launch { liveData.value = all() }
        liveData
    }

    open val liveData get() = repo.liveData

    open suspend fun all() = repo.all()

    open suspend fun allCatching() = repo.allCatching()

    suspend fun loadAll() = all()

    suspend fun loadAllCatching() = Result.catching { loadAll() }
}