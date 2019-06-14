package com.asofttz.persist

import com.asofttz.rx.ObservableList

abstract class Dao<T> : Lockable {
    override var isRunning = false

    protected val cached = ObservableList<T>()

    open suspend fun filter(predicate: (T) -> Boolean) = cached.value.filter(predicate)
    open suspend fun create(t: T): T? = t.also { cached.add(it) }
    open suspend fun edit(t: T): T? = t
    open suspend fun delete(t: T): T? = t.also { cached.remove(it) }
    open suspend fun load(id: Any): T? = cached.value.getOrNull(id.toString().toInt())
    open suspend fun loadAll(): ObservableList<T> = cached
}