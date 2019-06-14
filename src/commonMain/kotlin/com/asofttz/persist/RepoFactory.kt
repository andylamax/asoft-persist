package com.asofttz.persist

import kotlin.reflect.KClass

object RepoFactory {
    val repos = mutableMapOf<KClass<*>, Repo<*>>()

    inline fun <reified T> getRepo(dao: Dao<T>) = (repos.getOrPut(T::class) {
        object : Repo<T>(dao) {}
    } as Repo<T>)
}