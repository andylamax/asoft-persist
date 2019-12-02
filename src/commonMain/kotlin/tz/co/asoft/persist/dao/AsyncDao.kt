package tz.co.asoft.persist.dao

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import tz.co.asoft.rx.lifecycle.LiveData

class AsyncDao<T : Any>(vararg individualDaos: IDao<out T>) : IDao<T> {
    override val liveData: LiveData<List<T>?>
        get() = LiveData(null)

    private val daos: List<IDao<T>> = individualDaos.map { it as IDao<T> }

    override suspend fun create(list: List<T>): List<T>? = coroutineScope {
        daos.map { async { it.create(list) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun create(t: T): T? = coroutineScope {
        daos.map { async { it.create(t) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun edit(list: List<T>): List<T>? = coroutineScope {
        daos.map { async { it.edit(list) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun edit(t: T): T? = coroutineScope {
        daos.map { async { it.edit(t) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun delete(list: List<T>): List<T>? = coroutineScope {
        daos.map { async { it.delete(list) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun delete(t: T): T? = coroutineScope {
        daos.map { async { it.delete(t) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun wipe(list: List<T>): List<T>? = coroutineScope {
        daos.map { async { it.wipe(list) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun wipe(t: T): T? = coroutineScope {
        daos.map { async { it.wipe(t) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun load(ids: List<Any>): List<T>? = coroutineScope {
        daos.map { async { it.load(ids) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun load(id: Any): T? = coroutineScope {
        daos.map { async { it.load(id) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun all(): List<T>? = coroutineScope {
        daos.map { async { it.all() } }.mapNotNull { it.await() }.firstOrNull()
    }
}