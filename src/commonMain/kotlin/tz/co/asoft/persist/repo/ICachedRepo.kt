package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.ICache
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.model.Entity

interface ICachedRepo<T : Entity> : ITwinRepo<T> {
    val dao: IDao<T>
    val cache: ICache<T>

    override val localDao: IDao<T> get() = cache
    override val remoteDao: IDao<T> get() = dao
}