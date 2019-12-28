package tz.co.asoft.persist.dao

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class AsyncDao<T : Any>(vararg individualDaos: IDao<out T>) : IDao<T> {

    private val daos: List<IDao<T>> = individualDaos.map { it as IDao<T> }

    override suspend fun create(list: List<T>): List<T> = coroutineScope {
        daos.map { async { it.create(list) } }.mapNotNull { it.await() }.flatten()
    }

    override suspend fun create(t: T): T = coroutineScope {
        daos.map { async { it.create(t) } }.mapNotNull { it.await() }.first()
    }

    override suspend fun edit(list: List<T>): List<T> = coroutineScope {
        daos.map { async { it.edit(list) } }.mapNotNull { it.await() }.flatten()
    }

    override suspend fun edit(t: T): T = coroutineScope {
        daos.map { async { it.edit(t) } }.mapNotNull { it.await() }.first()
    }

    override suspend fun delete(list: List<T>): List<T> = coroutineScope {
        daos.map { async { it.delete(list) } }.mapNotNull { it.await() }.flatten()
    }

    override suspend fun delete(t: T): T = coroutineScope {
        daos.map { async { it.delete(t) } }.mapNotNull { it.await() }.first()
    }

    override suspend fun wipe(list: List<T>): List<T> = coroutineScope {
        daos.map { async { it.wipe(list) } }.mapNotNull { it.await() }.flatten()
    }

    override suspend fun wipe(t: T): T = coroutineScope {
        daos.map { async { it.wipe(t) } }.mapNotNull { it.await() }.first()
    }

    override suspend fun load(ids: List<Any>): List<T> = coroutineScope {
        daos.map { async { it.load(ids) } }.mapNotNull { it.await() }.flatten()
    }

    override suspend fun load(id: String): T? = coroutineScope {
        daos.map { async { it.load(id) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun all(): List<T> = coroutineScope {
        daos.map { async { it.all() } }.mapNotNull { it.await() }.flatten()
    }
}