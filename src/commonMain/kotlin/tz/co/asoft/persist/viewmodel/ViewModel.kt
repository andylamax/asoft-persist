package tz.co.asoft.persist.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.rx.lifecycle.LiveData
import kotlin.coroutines.CoroutineContext
import kotlin.js.JsName

abstract class ViewModel<T>(parentJob: Job? = null, private val repo: Repo<T>) : CoroutineScope {

    private val job = Job(parentJob)

    override val coroutineContext: CoroutineContext get() = job

    open suspend fun filter(predicate: (T) -> Boolean) = repo.filter(predicate)

    open suspend fun create(t: T) = repo.create(t)

    open suspend fun edit(t: T) = repo.edit(t)

    open suspend fun delete(t: T) = repo.delete(t)

    open suspend fun load(id: Any) = repo.load(id)

    @Deprecated(message = "Doesn't work well with more than one client", replaceWith = ReplaceWith("User all instead"))
    open suspend fun loadAll() = repo.loadAll()

    @JsName("allObservables")
    open val all = LiveData(repo.cache.toList())
        get() {
            field.value = repo.cache.toList()
            launch { field.value = all() }
            return field
        }

    open suspend fun all() = repo.all()
}