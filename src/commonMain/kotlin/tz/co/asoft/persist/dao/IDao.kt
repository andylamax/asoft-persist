package tz.co.asoft.persist.dao

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import tz.co.asoft.persist.result.Result
import tz.co.asoft.rx.lifecycle.ILifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

interface IDao<T : Any> {
    @Deprecated("Use flows only")
    val liveData: LiveData<List<T>?>

    suspend fun filter(predicate: (T) -> Boolean) = liveData.value?.filter(predicate)

    suspend fun create(list: List<T>): List<T>? = coroutineScope {
        list.map { async { create(it) } }.mapNotNull { it.await() }
    }

    suspend fun createCatching(list: List<T>) = Result.catching { create(list) }

    suspend fun create(t: T): T? = t

    suspend fun createCatching(t: T) = Result.catching { create(t) }

    suspend fun edit(list: List<T>): List<T>? = coroutineScope {
        list.map { async { edit(it) } }.mapNotNull { it.await() }
    }

    suspend fun editCatching(list: List<T>) = Result.catching { edit(list) }

    suspend fun edit(t: T): T? = t

    suspend fun editCatching(t: T) = Result.catching { edit(t) }

    suspend fun delete(list: List<T>): List<T>? = coroutineScope {
        list.map { async { delete(it) } }.mapNotNull { it.await() }
    }

    suspend fun deleteCatching(list: List<T>) = Result.catching { delete(list) }

    suspend fun delete(t: T): T? = t

    suspend fun deleteCatching(t: T) = Result.catching { delete(t) }

    suspend fun wipe(list: List<T>): List<T>? = coroutineScope {
        list.map { async { wipe(it) } }.mapNotNull { it.await() }
    }

    suspend fun wipeCatching(list: List<T>) = Result.catching { wipe(list) }

    suspend fun wipe(t: T): T? = t

    suspend fun wipeCatching(t: T) = Result.catching { wipe(t) }

    suspend fun load(ids: List<Any>): List<T>? = listOf()

    suspend fun loadCatching(ids: List<Any>) = Result.catching { load(ids) }

    suspend fun load(id: Any): T? = load(listOf(id))?.firstOrNull()

    suspend fun loadCatching(id: Any) = Result.catching { load(id) }

    @Deprecated("Use flows")
    suspend fun observe(lifeCycle: ILifeCycle, onChange: (List<T>?) -> Unit) = getLiveData().observe(lifeCycle, onChange)

    @Deprecated("Use flows")
    suspend fun observeCatching(lifeCycle: ILifeCycle, onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch {
            val res = allCatching()
            res.data?.let { liveData.value = it }
            res.cause?.let { if (liveData.value == null) newLiveData.value = res }
        }
        newLiveData.observe(lifeCycle, onChange)
    }

    @Deprecated("Use flows")
    suspend fun observeForever(onChange: (List<T>?) -> Unit) = getLiveData().observeForever(onChange)

    @Deprecated("Use flows")
    suspend fun observeForeverCatching(onChange: (Result<List<T>>) -> Unit) = coroutineScope {
        val newLiveData = liveData.map { Result(it) }
        launch {
            val res = allCatching()
            res.data?.let { liveData.value = it }
            res.cause?.let { if (liveData.value == null) newLiveData.value = res }
        }
        newLiveData.observeForever(onChange)
    }

    @Deprecated("Use flows")
    suspend fun getLiveData(): LiveData<List<T>?> = coroutineScope {
        launch { liveData.value = all() }
        liveData
    }

    suspend fun all(): List<T>? = listOf()

    suspend fun allCatching() = Result.catching { all() }

    suspend fun loadAll() = all()

    suspend fun loadAllCatching() = Result.catching { loadAll() }
}