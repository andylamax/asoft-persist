package tz.co.asoft.persist.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tz.co.asoft.persist.dao.ICache
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.model.Entity
import tz.co.asoft.persist.result.Result

interface ITwinRepo<T : Entity> : IRepo<T> {
    val remoteDao: IDao<T>
    val localDao: IDao<T>

    override suspend fun create(t: T): T? = remoteDao.create(t)?.also { localDao.create(it) }

    override suspend fun edit(t: T): T? = remoteDao.edit(t)?.also { localDao.edit(it) }

    override suspend fun delete(t: T): T? = remoteDao.delete(t)?.also { localDao.delete(it) }

    override suspend fun wipe(t: T): T? = remoteDao.wipe(t)?.also { localDao.wipe(t) }

    override suspend fun load(id: Any): T? = localDao.load(id).also {
        remoteDao.load(id)?.let { localDao.create(it) }
    }

    override suspend fun all(): List<T>? = localDao.all().apply {
        remoteDao.all()?.let { localDao.create(it) }
    }

    override fun allFlow(): Flow<List<T>> = flow {
        localDao.all()?.let { emit(it) }
        remoteDao.all()?.let {
            emit(it)
            localDao.create(it)
        }
    }

    override fun allFlowCatching(): Flow<Result<List<T>>> = flow {
        val cacheRes = localDao.allCatching()
        if (cacheRes.data != null) {
            emit(cacheRes)
        }
        val daoRes = remoteDao.allCatching()
        emit(daoRes)
        daoRes.data?.let { localDao.create(it) }
    }

    override fun loadFlowing(id: Any): Flow<T> = flow {
        localDao.load(id)?.let { emit(it) }
        remoteDao.load(id)?.let {
            emit(it)
            localDao.create(it)
        }
    }

    override fun loadFlowCatching(id: Any): Flow<Result<T>> = flow {
        val cacheRes = localDao.loadCatching(id)
        if (cacheRes.data != null) {
            emit(cacheRes)
        }
        val daoRes = remoteDao.loadCatching(id)
        emit(daoRes)
        daoRes.data?.let { localDao.create(it) }
    }
}