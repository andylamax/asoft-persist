package tz.co.asoft.persist.dao

import tz.co.asoft.rx.lifecycle.LiveData

open class Dao<T : Any> : IDao<T> {
    override val liveData = LiveData<List<T>?>(null)
}