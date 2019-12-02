package tz.co.asoft.persist.viewmodel

import tz.co.asoft.persist.repo.IRepo
import tz.co.asoft.persist.repo.PaginatedRepo
import tz.co.asoft.persist.repo.Repo
import kotlin.reflect.KClass

@Deprecated("Consider creating a new ViewModel instance when needed, and not from the factory")
object ViewModelFactory {
    val viewModels = mutableMapOf<KClass<*>, ViewModel<*>>()
    val paginatedViewModels = mutableMapOf<KClass<*>, ViewModel<*>>()

    inline fun <reified T : Any> getViewModel(repo: IRepo<T>) = (viewModels.getOrPut(T::class) { ViewModel(repo) } as ViewModel<T>)

    inline fun <reified T : Any> getViewModel(repo: PaginatedRepo<T>) = (paginatedViewModels.getOrPut(T::class) {
        object : PaginatedViewModel<T>(repo) {}
    } as PaginatedViewModel<T>)
}