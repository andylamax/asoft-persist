package tz.co.asoft.persist.dao

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.persist.result.Result
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

interface IDao<T> {
    val liveData: LiveData<List<T>?>
    
    suspend fun filter(predicate: (T) -> Boolean) = liveData.value?.filter(predicate)

    suspend fun create(list: List<T>): List<T>? = list

    suspend fun createCatching(list: List<T>) = Result.catching { create(list) }

    suspend fun create(t: T): T? = create(listOf(t))?.firstOrNull()

    suspend fun createCatching(t: T) = Result.catching { create(t) }

    suspend fun edit(list: List<T>): List<T>? = list

    suspend fun editCatching(list: List<T>) = Result.catching { edit(list) }

    suspend fun edit(t: T): T? = edit(listOf(t))?.firstOrNull()

    suspend fun editCatching(t: T) = Result.catching { edit(t) }

    suspend fun delete(list: List<T>): List<T>? = list

    suspend fun deleteCatching(list: List<T>) = Result.catching { delete(list) }

    suspend fun delete(t: T): T? = delete(listOf(t))?.firstOrNull()

    suspend fun deleteCatching(t: T) = Result.catching { delete(t) }

    suspend fun load(ids: List<Any>): List<T>? = listOf()

    suspend fun loadCatching(ids: List<Any>) = Result.catching { load(ids) }

    suspend fun load(id: Any): T? = load(listOf(id))?.firstOrNull()

    suspend fun loadCatching(id: Any) = Result.catching { load(id) }

    suspend fun observe(lifeCycle: LifeCycle, onChange: (List<T>?) -> Unit) = getLiveData().observe(lifeCycle, onChange)

    suspend fun observeCatching(lifeCycle: LifeCycle, onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch {
            val res = allCatching()
            res.data?.let { liveData.value = it }
            res.cause?.let { if (liveData.value == null) newLiveData.value = res }
        }
        newLiveData.observe(lifeCycle, onChange)
    }

    suspend fun observeForever(onChange: (List<T>?) -> Unit) = getLiveData().observeForever(onChange)

    suspend fun observeForeverCatching(onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch {
            val res = allCatching()
            res.data?.let { liveData.value = it }
            res.cause?.let { if (liveData.value == null) newLiveData.value = res }
        }
        newLiveData.observeForever(onChange)
    }

    suspend fun getLiveData(): LiveData<List<T>?> = coroutineScope {
        launch { liveData.value = all() }
        liveData
    }

    suspend fun all(): List<T>? = listOf()

    suspend fun allCatching() = Result.catching { all() }

    suspend fun loadAll() = all()

    suspend fun loadAllCatching() = Result.catching { loadAll() }
}