package tz.co.asoft.persist.repo

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import tz.co.asoft.persist.dao.ICache
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.model.Entity
import tz.co.asoft.persist.result.Result
import tz.co.asoft.persist.tools.Cause

interface ITwinRepo<T : Entity> : IRepo<T> {
    val remoteDao: IDao<T>
    val localDao: IDao<T>

    override suspend fun create(t: T) = remoteDao.create(t).also { localDao.create(it) }

    override suspend fun edit(t: T) = remoteDao.edit(t).also { localDao.edit(it) }

    override suspend fun delete(t: T) = remoteDao.delete(t).also { localDao.delete(it) }

    override suspend fun wipe(t: T) = remoteDao.wipe(t).also { localDao.wipe(t) }

    @Deprecated("Try using loadFlowing")
    override suspend fun load(id: String): T? {
        val localRes = localDao.load(id)
        return if (localRes != null) {
            GlobalScope.launch { remoteDao.load(id)?.also { localDao.create(it) } }
            localRes
        } else {
            remoteDao.load(id)?.also { localDao.create(it) }
        }
    }

    override fun loadFlowing(id: String) = flow {
        localDao.load(id)?.let { emit(it) }

        remoteDao.load(id).let {
            emit(it)
            if (it != null) localDao.create(it)
        }
    }

    override fun loadFlowing(ids: List<Any>) = flow {
        emit(localDao.load(ids))
        emit(remoteDao.load(ids))
    }

    override suspend fun all() = localDao.all().apply {
        remoteDao.all().let { localDao.create(it) }
    }

    override fun allFlowing() = flow {
        if (localDao.all().isNotEmpty()) emit(localDao.all())
        remoteDao.all().let {
            emit(it)
            localDao.create(it)
        }
    }
}