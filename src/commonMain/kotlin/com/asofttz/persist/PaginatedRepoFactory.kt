package com.asofttz.persist

import kotlin.reflect.KClass

object PaginatedRepoFactory {
    val repos = mutableMapOf<KClass<*>, PaginatedRepo<*>>()

    inline fun <reified T> getRepo(dao: PaginatedDao<T>) = (repos.getOrPut(T::class) {
        object : PaginatedRepo<T>(dao) {}
    } as PaginatedRepo<T>)
}