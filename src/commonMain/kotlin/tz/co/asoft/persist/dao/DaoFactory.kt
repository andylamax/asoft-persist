package tz.co.asoft.persist.dao

import kotlin.reflect.KClass

object DaoFactory {
    val daos = mutableMapOf<KClass<*>, Dao<*>>()
    inline fun <reified T, D : Dao<T>> getDao(builder: () -> D): D {
        return daos.getOrPut(T::class, builder) as D
    }
}