package com.asofttz.persist

import com.asofttz.persist.memory.Memory
import com.asofttz.persist.memory.ObservableMemory

abstract class PaginatedDao<T> : Lockable {
    override var isRunning = false

    protected var memory = ObservableMemory(Memory<T>())

    open suspend fun filter(predicate: (T) -> Boolean) = memory.getAll().filter(predicate)
    open suspend fun create(t: T): T? = memory.add(t).run { t }
    open suspend fun edit(t: T): T? = t
    open suspend fun delete(t: T): T? = memory.remove(t).run { t }
    open suspend fun load(id: Any): T? = memory.getAll().getOrNull(id.toString().toInt())
    open suspend fun loadAll() = memory.getAll()
    open suspend fun getMemory() = memory
    open suspend fun loadPage(pageNumber: Int) = memory.pages.getOrElse(pageNumber) { null }
}