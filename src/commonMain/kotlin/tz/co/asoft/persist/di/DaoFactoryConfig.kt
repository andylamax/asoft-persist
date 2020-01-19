package tz.co.asoft.persist.di

import kotlinx.atomicfu.AtomicRef
import kotlinx.atomicfu.atomic
import tz.co.asoft.persist.tools.Cause

abstract class DaoFactoryConfig<T : Any> {
    private val daoConfig: AtomicRef<T?> = atomic(null)
    val dao get() = daoConfig.value ?: throw Cause("Dao Factory is not yet configured")
    fun config(c: T) {
        daoConfig.value = c
    }
}