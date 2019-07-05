package tz.co.asoft.persist.viewmodel

import tz.co.asoft.persist.repo.PaginatedRepo
import tz.co.asoft.persist.repo.Repo
import kotlin.reflect.KClass

object ViewModelFactory {
    val viewModels = mutableMapOf<KClass<*>, ViewModel<*>>()
    val paginatedViewModels = mutableMapOf<KClass<*>, ViewModel<*>>()

    inline fun <reified T> getViewModel(repo: Repo<T>) = (viewModels.getOrPut(T::class) {
        object : ViewModel<T>(repo) {}
    } as ViewModel<T>)

    inline fun <reified T> getViewModel(repo: PaginatedRepo<T>) = (paginatedViewModels.getOrPut(T::class) {
        object : PaginatedViewModel<T>(repo) {}
    } as PaginatedViewModel<T>)
}