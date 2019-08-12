package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.dao.PaginatedDao
import kotlin.reflect.KClass

object RepoFactory {
    val repos = mutableMapOf<KClass<*>, Repo<*>>()
    val paginatedRepos = mutableMapOf<KClass<*>, PaginatedRepo<*>>()
    
    inline fun <reified T> getRepo(dao: Dao<T>) = (repos.getOrPut(T::class) {
        object : Repo<T>(dao) {}
    } as Repo<T>)

    inline fun <reified T> getRepo(dao: PaginatedDao<T>) = (paginatedRepos.getOrPut(T::class) {
        object : PaginatedRepo<T>(dao) {}
    } as PaginatedRepo<T>)
}