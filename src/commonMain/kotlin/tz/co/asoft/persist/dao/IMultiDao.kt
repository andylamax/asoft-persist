package tz.co.asoft.persist.dao

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.reflect.KClass

interface IMultiDao<T : Any> : IDao<T> {
    val daos: MutableMap<KClass<out T>, IDao<T>>

    //    private val T.dao: IDao<T>? get() = daos.keys.firstOrNull { this.isInstanceOf(it) }?.let { daos[it] }
    private val T.dao: IDao<T>?
        get() = daos.keys.firstOrNull { it.isInstance(this) }?.let { daos[it] }

    override suspend fun create(t: T) = t.dao?.create(t)!!
    override suspend fun edit(t: T) = t.dao?.edit(t)!!
    override suspend fun delete(t: T) = t.dao?.delete(t)!!
    override suspend fun wipe(t: T) = t.dao?.wipe(t)!!

    override suspend fun load(id: String): T? = coroutineScope {
        daos.values.map { async { it.load(id) } }.mapNotNull { it.await() }.firstOrNull()
    }

    override suspend fun all(): List<T> = loadAllByType().values.flatten()

    suspend fun loadAllByType(): Map<KClass<out T>, List<T>> = coroutineScope {
        daos.mapValues { (_, dao) -> async { dao.all() } }.mapValues { (_, defs) ->
            defs.await()
        }
    }

    suspend fun <E : T> loadAll(clazz: KClass<E>): List<E> = daos[clazz]?.all() as List<E>
}