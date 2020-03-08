package tz.co.asoft.persist.dao

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import tz.co.asoft.persist.result.catching

interface IDao<T : Any> {

    suspend fun create(list: List<T>): List<T> = coroutineScope {
        list.map { async { create(it) } }.mapNotNull { it.await() }
    }

    @Deprecated("Use createFlowing instead")
    suspend fun createCatching(list: List<T>) = catching { create(list) }

    fun createFlowing(list: List<T>) = flow { emit(create(list)) }

    suspend fun create(t: T): T

    @Deprecated("Use createFlowing")
    suspend fun createCatching(t: T) = catching { create(t) }

    fun createFlowing(t: T) = flow { emit(create(t)) }

    suspend fun edit(list: List<T>): List<T> = coroutineScope {
        list.map { async { edit(it) } }.mapNotNull { it.await() }
    }

    @Deprecated("Use editFlowing")
    suspend fun editCatching(list: List<T>) = catching { edit(list) }

    fun editFlowing(list: List<T>) = flow { emit(edit(list)) }

    suspend fun edit(t: T): T

    @Deprecated("use editFlowing")
    suspend fun editCatching(t: T) = catching { edit(t) }

    fun editFlowing(t: T) = flow { emit(edit(t)) }

    suspend fun delete(list: List<T>): List<T> = coroutineScope {
        list.map { async { delete(it) } }.mapNotNull { it.await() }
    }

    @Deprecated("use deleteFlowing")
    suspend fun deleteCatching(list: List<T>) = catching { delete(list) }

    fun deleteFlowing(list: List<T>) = flow { emit(delete(list)) }

    suspend fun delete(t: T): T

    @Deprecated("use deleteFlowing")
    suspend fun deleteCatching(t: T) = catching { delete(t) }

    fun deleteFlowing(t: T) = flow { emit(delete(t)) }

    suspend fun wipe(list: List<T>): List<T> = coroutineScope {
        list.map { async { wipe(it) } }.mapNotNull { it.await() }
    }

    @Deprecated("use wipeFlowing")
    suspend fun wipeCatching(list: List<T>) = catching { wipe(list) }

    fun wipeFlowing(list: List<T>) = flow { emit(wipe(list)) }

    suspend fun wipe(t: T): T

    @Deprecated("use wipeFlowing")
    suspend fun wipeCatching(t: T) = catching { wipe(t) }

    fun wipeFlowing(t: T) = flow { emit(wipe(t)) }

    suspend fun load(ids: List<Any>): List<T> = coroutineScope {
        ids.map { async { load(it.toString()) } }.mapNotNull { it.await() }
    }

    @Deprecated(" use loadFlowing")
    suspend fun loadCatching(ids: List<Any>) = catching { load(ids) }

    fun loadFlowing(ids: List<Any>) = flow { emit(load(ids)) }

    suspend fun load(id: Number): T? = load(id.toString())

    @Deprecated("use loadFlowing")
    suspend fun loadCatching(id: Number) = catching { load(id) }

    fun loadFlowing(id: Number) = flow { emit(load(id)) }

    suspend fun load(id: String): T?

    @Deprecated("use loadFlowing")
    suspend fun loadCatching(id: String) = catching { load(id) }

    fun loadFlowing(id: String) = flow { emit(load(id)) }

    suspend fun all(): List<T>

    @Deprecated("use allFlowing")
    suspend fun allCatching() = catching { all() }

    fun allFlowing() = flow { emit(all()) }

    @Deprecated("use all()")
    suspend fun loadAll() = all()

    @Deprecated("use allFlowing()")
    suspend fun loadAllCatching() = catching { loadAll() }
}