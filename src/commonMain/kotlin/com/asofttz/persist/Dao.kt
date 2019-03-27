package com.asofttz.persist

import com.asofttz.rx.ObservableList

abstract class Dao<T> {
    protected val cached = ObservableList<T>()

    open suspend fun filter(predicate: (T) -> Boolean) = cached.value.filter(predicate)
    open suspend fun create(t: T): Boolean = cached.add(t)
    open suspend fun edit(t: T): Boolean = true
    open suspend fun delete(t: T): Boolean = cached.remove(t)
    open suspend fun load(id: Int): T? = cached.value.getOrNull(id)
    open suspend fun loadAll(): ObservableList<T> = cached
}