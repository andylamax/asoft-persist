package tz.co.asoft.persist.viewmodel

import kotlinx.coroutines.Job
import tz.co.asoft.persist.repo.PaginatedRepo
import tz.co.asoft.persist.repo.Repo
import kotlin.reflect.KClass

object ViewModelFactory {
    val viewModels = mutableMapOf<KClass<*>, ViewModel<*>>()
    val paginatedViewModels = mutableMapOf<KClass<*>, ViewModel<*>>()

    inline fun <reified T> getViewModel(parentJob: Job? = null, repo: Repo<T>) = (viewModels.getOrPut(T::class) {
        object : ViewModel<T>(parentJob, repo) {}
    } as ViewModel<T>)

    inline fun <reified T> getViewModel(parentJob: Job? = null, repo: PaginatedRepo<T>) = (paginatedViewModels.getOrPut(T::class) {
        object : PaginatedViewModel<T>(parentJob, repo) {}
    } as PaginatedViewModel<T>)
}