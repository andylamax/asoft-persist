package com.asofttz.persist

import kotlin.reflect.KClass

object ViewModelFactory {
    val viewModels = mutableMapOf<KClass<*>, ViewModel<*>>()

    inline fun <reified T> getViewModel(repo: Repo<T>) = (viewModels.getOrPut(T::class) {
        object : ViewModel<T>(repo) {}
    } as ViewModel<T>)
}