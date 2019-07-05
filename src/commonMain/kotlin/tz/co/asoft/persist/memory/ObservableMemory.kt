package tz.co.asoft.persist.memory

import tz.co.asoft.rx.observers.Observable

class ObservableMemory<T>(initialMemory: Memory<T>) : Observable<Memory<T>>(initialMemory) {
    val size get() = value.size

    val perPage get() = value.perPage

    val pages get() = value.pages

    fun remove(t: T) = value.remove(t).also {
        dispatch()
    }

    fun addPage(page: Page<T>) = value.addPage(page).also {
        dispatch()
    }

    fun add(t: T) = value.add(t).also {
        dispatch()
    }

    fun addAll(data: Collection<T>) = value.addAll(data).also {
        dispatch()
    }

    fun getAll() = value.getAll()
}