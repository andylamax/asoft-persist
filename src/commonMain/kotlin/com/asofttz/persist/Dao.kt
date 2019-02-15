package com.asofttz.persist

import com.asofttz.rx.ObservableList

abstract class Dao<T> {
    protected val cached = ObservableList<T>()

    suspend fun filter(predicate: (T)->Boolean) = cached.value.filter(predicate)
    abstract suspend fun create(t: T): Boolean
    abstract suspend fun edit(t: T): Boolean
    abstract suspend fun delete(t: T): Boolean
    abstract suspend fun load(id: Int): T?
    abstract suspend fun loadAll(): ObservableList<T>
}