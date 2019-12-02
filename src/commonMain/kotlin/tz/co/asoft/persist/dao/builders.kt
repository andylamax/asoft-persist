package tz.co.asoft.persist.dao

import kotlin.reflect.KClass

@Deprecated("Use single/only")
object DaoFactory {
    val daos = mutableMapOf<KClass<*>, IDao<*>>()
    inline fun <reified T, D : IDao<T>> getDao(builder: () -> D): D {
        return daos.getOrPut(T::class, builder) as D
    }
}