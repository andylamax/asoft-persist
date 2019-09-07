package tz.co.asoft.persist.dao

import tz.co.asoft.persist.memory.Memory
import tz.co.asoft.rx.lifecycle.LiveData

abstract class PaginatedDao<T> : Dao<T>() {
    open val memoryLive = LiveData(Memory<T>())
    open suspend fun getMemory() = memoryLive.value
    open suspend fun loadPage(pageNumber: Int) = getMemory().pages.getOrElse(pageNumber) { null }
}