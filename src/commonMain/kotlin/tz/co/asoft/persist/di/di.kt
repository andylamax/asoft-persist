package tz.co.asoft.persist.di

import kotlinx.atomicfu.atomic
import tz.co.asoft.persist.dao.ICache
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.repo.IRepo
import kotlin.reflect.KClass

@PublishedApi
internal val singletons = atomic(mutableMapOf<KClass<*>, Any>())

inline fun <reified T : Any> single(noinline block: () -> T): T = singletons.getOrPut(T::class, block) as T

@PublishedApi
internal val caches = atomic(mutableMapOf<KClass<*>, IDao<*>>())

inline fun <reified E : Any, C : ICache<E>> onlyCache(noinline block: () -> C) = caches.getOrPut(E::class, block) as C

inline fun <reified E : Any, C : ICache<E>> cache(noinline block: () -> C) = onlyCache(block)

@PublishedApi
internal val daos = atomic(mutableMapOf<KClass<*>, IDao<*>>())

inline fun <reified E : Any, D : IDao<E>> onlyDao(noinline block: () -> D) = daos.getOrPut(E::class, block) as D

inline fun <reified E : Any, D : IDao<E>> dao(noinline block: () -> D) = onlyDao(block)

@PublishedApi
internal val repos = atomic(mutableMapOf<KClass<*>, IRepo<*>>())

inline fun <reified E : Any, R : IRepo<E>> onlyRepo(noinline block: () -> R) = repos.getOrPut(E::class, block) as R

inline fun <reified E : Any, R : IRepo<E>> repo(noinline block: () -> R) = onlyRepo(block)