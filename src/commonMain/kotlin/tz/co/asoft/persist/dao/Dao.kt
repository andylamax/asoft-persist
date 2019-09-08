package tz.co.asoft.persist.dao

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.persist.result.Result
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

abstract class Dao<T> {

    open suspend fun filter(predicate: (T) -> Boolean) = liveData.value?.filter(predicate)

    open suspend fun create(list: List<T>): List<T>? = list

    open suspend fun createCatching(list: List<T>) = Result.catching { create(list) }

    open suspend fun create(t: T): T? = create(listOf(t))?.firstOrNull()

    open suspend fun createCatching(t: T) = Result.catching { create(t) }

    open suspend fun edit(list: List<T>): List<T>? = list

    open suspend fun editCatching(list: List<T>) = Result.catching { edit(list) }

    open suspend fun edit(t: T): T? = edit(listOf(t))?.firstOrNull()

    open suspend fun editCatching(t: T) = Result.catching { edit(t) }

    open suspend fun delete(list: List<T>): List<T>? = list

    open suspend fun deleteCatching(list: List<T>) = Result.catching { delete(list) }

    open suspend fun delete(t: T): T? = delete(listOf(t))?.firstOrNull()

    open suspend fun deleteCatching(t: T) = Result.catching { delete(t) }

    open suspend fun load(ids: List<Any>): List<T>? = listOf()

    open suspend fun loadCatching(ids: List<Any>) = Result.catching { load(ids) }

    open suspend fun load(id: Any): T? = load(listOf(id))?.firstOrNull()

    open suspend fun loadCatching(id: Any) = Result.catching { load(id) }

    open suspend fun observe(lifeCycle: LifeCycle, onChange: (List<T>?) -> Unit) = getLiveData().observe(lifeCycle, onChange)

    open suspend fun observeCatching(lifeCycle: LifeCycle, onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch { newLiveData.value = allCatching() }
        newLiveData.observe(lifeCycle,onChange)
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

    open val liveData = LiveData<List<T>?>(null)

    @Deprecated("Use getLiveData Instead")
    open val allLive = LiveData<List<T>?>(null)
        get() {
            GlobalScope.launch { field.value = all() }
            return field
        }

    open suspend fun all(): List<T>? = listOf()

    open suspend fun allCatching() = Result.catching { all() }

    suspend fun loadAll() = all()

    suspend fun loadAllCatching() = Result.catching { loadAll() }
}