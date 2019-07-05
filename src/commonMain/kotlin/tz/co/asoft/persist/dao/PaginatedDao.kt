package tz.co.asoft.persist.dao

import tz.co.asoft.persist.memory.Memory
import tz.co.asoft.persist.memory.ObservableMemory

abstract class PaginatedDao<T> : Dao<T>() {
    protected var memory = ObservableMemory(Memory<T>())
    open suspend fun getMemory() = memory
    open suspend fun loadPage(pageNumber: Int) = memory.pages.getOrElse(pageNumber) { null }
}