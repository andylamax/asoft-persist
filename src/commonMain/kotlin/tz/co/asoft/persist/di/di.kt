package tz.co.asoft.persist.di

import tz.co.asoft.persist.dao.ICache
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.repo.IRepo
import kotlin.reflect.KClass

@PublishedApi
internal val singletons by lazy { mutableMapOf<KClass<*>, Any>() }

inline fun <reified T : Any> single(block: () -> T): T = singletons.getOrPut(T::class, block) as T

@PublishedApi
internal val caches by lazy { mutableMapOf<KClass<*>, IDao<*>>() }

inline fun <reified E : Any, C : ICache<E>> onlyCache(block: () -> C) = caches.getOrPut(E::class, block) as C

inline fun <reified E : Any, C : ICache<E>> cache(block: () -> C) = onlyCache(block)

@PublishedApi
internal val daos by lazy { mutableMapOf<KClass<*>, IDao<*>>() }

inline fun <reified E : Any, D : IDao<E>> onlyDao(block: () -> D) = daos.getOrPut(E::class, block) as D

inline fun <reified E : Any, D : IDao<E>> dao(block: () -> D) = onlyDao(block)

@PublishedApi
internal val repos by lazy { mutableMapOf<KClass<*>, IRepo<*>>() }

inline fun <reified E : Any, R : IRepo<E>> onlyRepo(block: () -> R) = repos.getOrPut(E::class, block) as R

inline fun <reified E : Any, R : IRepo<E>> repo(block: () -> R) = onlyRepo(block)