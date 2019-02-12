package com.asofttz.persist

abstract class Dao<T> {
    protected val cached = mutableListOf<T>()

    abstract suspend fun create(t: T) : Result<T>
    abstract suspend fun edit(t: T) : Result<T>
    abstract suspend fun delete(t: T): Result<T>
    abstract suspend fun load(id: Int): Result<T>
    abstract suspend fun loadAll()
}