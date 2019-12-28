package tz.co.asoft.persist.dao

import tz.co.asoft.persist.model.Entity

inline fun <reified T : Entity> cache() = tz.co.asoft.persist.di.cache { Cache<T>() }