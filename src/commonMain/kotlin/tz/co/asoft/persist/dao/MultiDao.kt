package tz.co.asoft.persist.dao

import kotlin.reflect.KClass

open class MultiDao<T : Any>(vararg individualDaos: Pair<KClass<out T>, IDao<out T>>) : IMultiDao<T> {
    override val daos = individualDaos.toMap().mapValues { (_, ds) -> ds as IDao<T> }.toMutableMap()
}