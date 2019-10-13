package tz.co.asoft.persist.di

import kotlin.reflect.KClass

val singletons by lazy { mutableMapOf<KClass<*>, Any>() }

inline fun <reified T : Any> single(block: () -> T): T = singletons.getOrPut(T::class, block) as T

inline fun <reified T : Any> only(block: () -> T) = single(block)