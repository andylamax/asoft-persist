package tz.co.asoft.persist.dao

import tz.co.asoft.persist.model.Entity
import tz.co.asoft.rx.lifecycle.LiveData

open class Cache<T : Entity> : ICache<T> {
    override val liveData = LiveData<List<T>?>(null)
    override var data: MutableMap<String, T>? = null
}