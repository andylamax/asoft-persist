package com.asofttz.persist

import kotlin.reflect.KClass

object PaginatedViewModelFactory {
    val viewModels = mutableMapOf<KClass<*>, PaginatedViewModel<*>>()

    inline fun <reified T> getViewModel(repo: PaginatedRepo<T>) = (viewModels.getOrPut(T::class) {
        object : PaginatedViewModel<T>(repo) {}
    } as PaginatedViewModel<T>)
}