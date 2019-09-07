package tz.co.asoft.persist.dao

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tz.co.asoft.rx.lifecycle.LifeCycle
import tz.co.asoft.rx.lifecycle.LiveData

abstract class Dao<T> {

    open suspend fun filter(predicate: (T) -> Boolean) = all().filter(predicate)

    open suspend fun create(list: List<T>): List<T>? = list

    open suspend fun create(t: T): T? = create(listOf(t))?.firstOrNull()

    open suspend fun edit(list: List<T>): List<T>? = list

    open suspend fun edit(t: T): T? = edit(listOf(t))?.firstOrNull()

    open suspend fun delete(list: List<T>): List<T>? = list

    open suspend fun delete(t: T): T? = delete(listOf(t))?.firstOrNull()

    open suspend fun load(ids: List<Any>): List<T>? = listOf()

    open suspend fun load(id: Any): T? = load(listOf(id))?.firstOrNull()

    open fun observe(lifeCycle: LifeCycle, onChange: (List<T>) -> Unit) = allLive.observe(lifeCycle, onChange)

    open val allLive = LiveData(mutableListOf<T>())
        get() {
            GlobalScope.launch { field.value = all().toMutableList() }
            return field
        }

    open suspend fun all(): List<T> = allLive.value
}