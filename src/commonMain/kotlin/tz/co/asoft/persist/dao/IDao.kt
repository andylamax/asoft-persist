package tz.co.asoft.persist.dao

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import tz.co.asoft.persist.result.catching

interface IDao<T : Any> {

    suspend fun create(list: List<T>): List<T> = coroutineScope {
        list.map { async { create(it) } }.mapNotNull { it.await() }
    }

    suspend fun createCatching(list: List<T>) = catching { create(list) }

    suspend fun create(t: T): T = t

    suspend fun createCatching(t: T) = catching { create(t) }

    suspend fun edit(list: List<T>): List<T> = coroutineScope {
        list.map { async { edit(it) } }.mapNotNull { it.await() }
    }

    suspend fun editCatching(list: List<T>) = catching { edit(list) }

    suspend fun edit(t: T): T = t

    suspend fun editCatching(t: T) = catching { edit(t) }

    suspend fun delete(list: List<T>): List<T> = coroutineScope {
        list.map { async { delete(it) } }.mapNotNull { it.await() }
    }

    suspend fun deleteCatching(list: List<T>) = catching { delete(list) }

    suspend fun delete(t: T): T = t

    suspend fun deleteCatching(t: T) = catching { delete(t) }

    suspend fun wipe(list: List<T>): List<T> = coroutineScope {
        list.map { async { wipe(it) } }.mapNotNull { it.await() }
    }

    suspend fun wipeCatching(list: List<T>) = catching { wipe(list) }

    suspend fun wipe(t: T): T = t

    suspend fun wipeCatching(t: T) = catching { wipe(t) }

    suspend fun load(ids: List<Any>): List<T> = coroutineScope {
        ids.map { async { load(it.toString()) } }.mapNotNull { it.await() }
    }

    suspend fun loadCatching(ids: List<Any>) = catching { load(ids) }

    suspend fun load(id: Number): T? = load(id.toString())

    suspend fun loadCatching(id: Number) = catching { load(id) }

    suspend fun load(id: String): T? = null

    suspend fun loadCatching(id: String) = catching { load(id) }

    suspend fun all(): List<T> = listOf()

    suspend fun allCatching() = catching { all() }

    suspend fun loadAll() = all()

    suspend fun loadAllCatching() = catching { loadAll() }
}