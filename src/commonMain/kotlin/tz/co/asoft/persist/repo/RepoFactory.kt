package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.dao.PaginatedDao
import kotlin.reflect.KClass

object RepoFactory {
    val repos = mutableMapOf<KClass<*>, Repo<*>>()
    val paginatedRepos = mutableMapOf<KClass<*>, PaginatedRepo<*>>()

    inline fun <reified T, D : Dao<T>, R : Repo<T>> getRepo(dao: D, builder: (D) -> R = { Repo(dao) as R }): R {
        return repos.getOrPut(T::class) { builder(dao) } as R
    }

    inline fun <reified T, D : PaginatedDao<T>, R : PaginatedRepo<R>> getRepo(dao: D, builder: (D) -> R = { PaginatedRepo(it) as R }): R {
        return paginatedRepos.getOrPut(T::class) { builder(dao) } as R
    }
}