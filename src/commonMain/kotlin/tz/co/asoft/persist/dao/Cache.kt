package tz.co.asoft.persist.dao

import tz.co.asoft.persist.model.Entity

open class Cache<T : Entity> : ICache<T> {
    override var data: MutableMap<String, T>? = null
}